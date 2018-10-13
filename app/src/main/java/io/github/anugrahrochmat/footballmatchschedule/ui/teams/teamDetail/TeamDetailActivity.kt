package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.github.anugrahrochmat.footballmatchschedule.data.models.TeamFavourite
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.tabLayoutTeamDetail.TabsLayoutTeam
import kotlinx.android.synthetic.main.activity_team_detail.*



class TeamDetailActivity : AppCompatActivity(), TeamDetailView {
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var team: Team
    private lateinit var teamId: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_TEAM = "team"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        team = intent.getParcelableExtra(EXTRA_TEAM)
        teamId = team.teamId!!

        loadTabLayoutTeam(team.teamDescription.toString(), team.teamName.toString())

        presenter = TeamDetailPresenter(this)
        presenter.loadTeamDetail(team)
        presenter.onViewAttached()

        favouriteState()
        
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavourite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_favourite -> {
                if (isFavorite) {
                    removeFromFavourite()
                } else {
                    addFavourite()
                }
                isFavorite = !isFavorite
                setFavourite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun favouriteState(){
        presenter.getTeamFavorites(teamId)
    }

    private fun setFavourite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_white_favorite_24dp)
        }
        else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_white_favorite_border_24dp)
        }
    }

    private fun removeFromFavourite(){
        presenter.deleteTeamFavorites(teamId)
    }

    private fun addFavourite(){
        presenter.insertTeamFavorites(team)
    }

    private fun loadTabLayoutTeam(teamDesc: String, teamName: String) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container_team_detail, TabsLayoutTeam.newInstance(teamDesc, teamName), TabsLayoutTeam::class.java.simpleName)
                .commit()
    }

    override fun showTeamDetail(team: Team) {
        tv_name_team_detail.text = team.teamName
        tv_birth_team_detail.text = team.formedYear
        tv_stadium_team_detail.text = team.teamStadium

        Picasso.get().load(team.teamBadge).noFade().into(img_logo_team_detail)
    }

    override fun getContext(): Context {
        return this
    }

    override fun showTeamFavourites(teamFavourite: List<TeamFavourite>) {
        if (!teamFavourite.isEmpty()) isFavorite = true
    }

    override fun showTeamFavoriteInserted(rowId: Long) {
        if (rowId > 0) {
            Snackbar.make(window.decorView.rootView, getString(R.string.team_favourite_added), Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(window.decorView.rootView, getString(R.string.error_add_favourite), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showTeamFavouriteDeleted(rowAffected: Int) {
        if (rowAffected > 0) {
            Snackbar.make(window.decorView.rootView, getString(R.string.team_favourite_removed), Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(window.decorView.rootView, getString(R.string.error_remove_favourite), Snackbar.LENGTH_LONG).show()
        }
    }
}
