package io.github.anugrahrochmat.footballmatchschedule.ui.teams.teamDetail.teamPlayers.playerDetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import io.github.anugrahrochmat.footballmatchschedule.R
import io.github.anugrahrochmat.footballmatchschedule.data.models.Player
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {

    private lateinit var presenter: PlayerDetailPresenter
    private lateinit var player: Player

    companion object {
        const val EXTRA_PLAYER = "player"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        player = intent.getParcelableExtra(EXTRA_PLAYER)
        title = player.playerName

        presenter = PlayerDetailPresenter(this)
        presenter.loadPlayerDetail(player)
        presenter.onViewAttached()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showPlayerDetail(player: Player) {
        tv_player_name_detail.text = player.playerName
        tv_player_nationality.text = player.playerNationality
        tv_player_position_detail.text = player.playerPosition
        tv_player_place_birth.text = player.playerBirthLoc
        tv_player_born.text = player.playerBirthDate
        tv_player_height.text = player.playerHeight
        tv_player_weight.text = player.playerWeight
        tv_player_detail_description.text = player.playerDesc

        Picasso.get().load(player.playerThumbImage).into(img_player_detail)
    }
}
