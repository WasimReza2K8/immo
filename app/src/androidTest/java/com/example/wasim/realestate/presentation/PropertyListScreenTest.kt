package com.example.wasim.realestate.presentation

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.core.ui.R.string
import com.example.wasim.MainActivity
import com.example.wasim.realestate.data.FakePropertyDao
import com.example.wasim.realestate.data.FakePropertyApi
import com.example.wasim.utils.ReturnType.NetworkException
import com.example.wasim.utils.ReturnType.UnknownException
import com.example.wasim.utils.ReturnType.ValidEmptyList
import com.wasim.feature.realestate.R
import com.wasim.feature.realestate.data.datasource.remote.api.PropertyApi
import com.wasim.feature.realestate.data.datasource.db.dao.PropertyDao
import com.wasim.feature.realestate.di.RealEstateDataModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(RealEstateDataModule::class)
@RunWith(AndroidJUnit4::class)
class PropertyListScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val androidComposeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var fakePropertyApi: PropertyApi

    @Inject
    lateinit var fakeDao: PropertyDao

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun test_PropertyListScreen_having_valid_result() {
        androidComposeTestRule.apply {
            onNodeWithTag(activity.getString(R.string.realestate_property_list))
                .onChildren()
                .assertCountEquals(3)
        }
    }

    @Test
    fun test_PropertyListScreen_having_empty_result() {
        (fakePropertyApi as? FakePropertyApi)?.returnType = ValidEmptyList

        androidComposeTestRule.apply {
            val result = activity.getString(R.string.realestate_no_property_found)
            onNodeWithText(result).assertIsDisplayed()
            onNodeWithTag(activity.getString(R.string.realestate_property_list)).assertDoesNotExist()
        }
    }

    @Test
    fun test_PropertyListScreen_having_only_network_error_result() {
        (fakePropertyApi as? FakePropertyApi)?.returnType = NetworkException
        (fakeDao as? FakePropertyDao)?.returnType = ValidEmptyList

        androidComposeTestRule.apply {
            val text = activity.getString(string.network_error)
            onNodeWithText(text)
            onNodeWithTag(activity.getString(R.string.realestate_property_list))
                .onChildren()
                .assertCountEquals(0)
        }
    }

    @Test
    fun test_PropertyListScreen_having_valid_list_with_network_error_result() {
        (fakePropertyApi as? FakePropertyApi)?.returnType = NetworkException

        androidComposeTestRule.apply {
            val text = activity.getString(string.network_error)
            onNodeWithText(text)
            onNodeWithTag(activity.getString(R.string.realestate_property_list))
                .onChildren()
                .assertCountEquals(3)
        }
    }

    @Test
    fun test_PropertyListScreen_having_only_unknown_error_result() {
        (fakePropertyApi as? FakePropertyApi)?.returnType = UnknownException
        (fakeDao as? FakePropertyDao)?.returnType = UnknownException

        androidComposeTestRule.apply {
            val text = activity.getString(string.unknown_error)
            onNodeWithText(text)
            onNodeWithTag(activity.getString(R.string.realestate_property_list))
                .onChildren()
                .assertCountEquals(0)
        }
    }

    @Test
    fun test_PropertyListScreen_having_valid_list_with_unknown_error_result() {
        (fakePropertyApi as? FakePropertyApi)?.returnType = UnknownException

        androidComposeTestRule.apply {
            val text = activity.getString(string.unknown_error)
            onNodeWithText(text)
            onNodeWithTag(activity.getString(R.string.realestate_property_list))
                .onChildren()
                .assertCountEquals(3)
        }
    }

    @Test
    fun test_PropertyListScreen_item_click_to_navigate() {
        androidComposeTestRule.apply {
            onNodeWithTag(activity.getString(R.string.realestate_property_list))
                .onChildren()[1]
                .performClick()

            onNodeWithText(activity.getString(R.string.realestate_detail)).assertIsDisplayed()
        }
    }
}
