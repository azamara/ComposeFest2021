import android.os.Build
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.test.filters.SdkSuppress
import com.example.compose.rally.RallyNavHost
import com.example.compose.rally.RallyScreen
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test

class RallyNavHostTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: NavHostController

    @Before
    fun setupRallyNavHost() {
        composeTestRule.setContent {
            navController = rememberNavController()
            RallyNavHost(navController = navController)
        }
    }

    @Test
    fun rallyNavHost() {
        composeTestRule
            .onNodeWithContentDescription("Overview Screen")
            .assertIsDisplayed()
    }


    @Test
    fun rallyNavHost_navigateToAllAccounts_viaUI() {
        composeTestRule
            .onNodeWithContentDescription("All Accounts")
            .performClick()
        composeTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyNavHost_navigateToBills_viaUI() {
        composeTestRule.onNodeWithContentDescription("All Bills").apply {
            performScrollTo()
            performClick()
        }
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, "Bills")
    }
    @Test
    fun rallyNavHost_navigateToAllAccounts_callingNavigate() {
        runBlocking {
            withContext(Dispatchers.Main) {
                navController.navigate(RallyScreen.Accounts.name)
            }
        }
        composeTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }
}