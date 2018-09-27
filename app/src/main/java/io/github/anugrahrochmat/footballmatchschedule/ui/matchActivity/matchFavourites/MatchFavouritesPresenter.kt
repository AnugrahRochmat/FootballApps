package io.github.anugrahrochmat.footballmatchschedule.ui.matchActivity.matchFavourites

import android.content.Context
import io.github.anugrahrochmat.footballmatchschedule.data.database.database
import io.github.anugrahrochmat.footballmatchschedule.data.models.Favourite
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class MatchFavouritesPresenter(private val context: Context, private val view: MatchFavouritesView){
    private var favourites: MutableList<Favourite> = mutableListOf()

    fun getFavouritesData(){
        favourites.clear()

        context.database.use {
            view.showLoading()
            val result = select(Favourite.TABLE_FAVOURITE)
            val favourite = result.parseList(classParser<Favourite>())
            favourites.addAll(favourite)

            if (!favourites.isEmpty()){
                view.hideLoading(true)
                view.showMatchFavourites(favourites)
            } else {
                view.hideLoading(false)
            }

        }
    }
}