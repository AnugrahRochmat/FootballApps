package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.models.Team
import io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.tabLayoutTeamDetail.TabsLayoutTeam
import kotlinx.android.synthetic.main.activity_team_detail.*



class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var team: Team

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

        loadTabLayoutTeam(team.teamDescription.toString(), team.teamName.toString())

        presenter = TeamDetailPresenter(this)
        presenter.loadTeamDetail(team)
        presenter.onViewAttached()
        
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
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
}
