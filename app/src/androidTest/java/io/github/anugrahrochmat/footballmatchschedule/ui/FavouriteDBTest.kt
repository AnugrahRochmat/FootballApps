package io.github.anugrahrochmat.footballmatchschedule.ui

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import io.github.anugrahrochmat.footballmatchschedule.data.database.DatabaseHelper
import io.github.anugrahrochmat.footballmatchschedule.data.database.FavouriteDBHelper
import io.github.anugrahrochmat.footballmatchschedule.data.models.MatchSchedule
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class FavouriteDBTest {

    private lateinit var context: Context
    private lateinit var databaseHelper: DatabaseHelper

    @Before
    fun setup() {
        context = InstrumentationRegistry.getTargetContext()
        databaseHelper = DatabaseHelper(context)
        databaseHelper.clearTable()
    }

    @After
    fun finish() {
        databaseHelper.clearTable()
    }

    @Test
    fun testNotNull() {
        Assert.assertNotNull(databaseHelper)
    }

    @Test
    fun testInsertOneData() {
        val matchId = "12"

        val rowId = insertFavorite(matchId)
        Assert.assertTrue(rowId > 0)
    }

    @Test
    fun testInsertTwoData(){
        val matchId1 = "12"
        val matchId2 = "22"

        val rowId1 = insertFavorite(matchId1)
        Assert.assertTrue(rowId1 > 0)

        val rowId2 = insertFavorite(matchId2)
        Assert.assertTrue(rowId2 > 0)
        Assert.assertTrue(rowId2 > rowId1)
    }

    @Test
    fun testGetSameId(){
        val matchID = "33"
        val rowId = insertFavorite(matchID)

        Assert.assertTrue(rowId > 0)

        val favouriteGet = FavouriteDBHelper.get(context, matchID)
        Assert.assertEquals(favouriteGet[0].matchID, matchID)
    }

    @Test
    fun testGetDifferentId(){
        val matchID = "333"
        val rowId = insertFavorite(matchID)

        Assert.assertTrue(rowId > 0)

        val favouriteGet = FavouriteDBHelper.get(context, "222")
        Assert.assertEquals(0, favouriteGet.size)
    }

    @Test
    fun testRemoveSuccess(){
        val matchId = "33"

        val rowId = insertFavorite(matchId)
        Assert.assertTrue(rowId > 0)

        val rowAffected = FavouriteDBHelper.delete(context, matchId)
        Assert.assertEquals(1, rowAffected)
        Assert.assertNotEquals(0, rowAffected)

        val favouriteGet = FavouriteDBHelper.get(context, matchId)
        Assert.assertEquals(0, favouriteGet.size)
    }

    private fun insertFavorite(id: String): Long{
        val urlHomeTeamBadge = ""
        val urlAwayTeamBadge = ""

        val match = MatchSchedule()
        match.matchId = id

        return FavouriteDBHelper.insert(context, match, urlHomeTeamBadge, urlAwayTeamBadge)
    }
}