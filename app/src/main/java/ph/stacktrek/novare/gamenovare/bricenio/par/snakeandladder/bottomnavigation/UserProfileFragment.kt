package ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.bottomnavigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.databinding.FragmentUserProfileBinding
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.utility.PreferenceUtility

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentUserProfileBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        PreferenceUtility(requireContext()).apply {
            binding.nicknameP1.setText(getStringPreferences("nickname1", "Player1"))
            binding.nicknameP2.setText(getStringPreferences("nickname2", "Player2"))
            binding.nicknameP3.setText(getStringPreferences("nickname3", "Player3"))
            binding.nicknameP4.setText(getStringPreferences("nickname4", "Player4"))
        }
        val btnsave = binding.btnUserprofile
        btnsave.setOnClickListener(){
            val nicknamePlayer1 = binding.nicknameP1.text.toString()
            val nicknamePlayer2 = binding.nicknameP2.text.toString()
            val nicknamePlayer3 = binding.nicknameP3.text.toString()
            val nicknamePlayer4 = binding.nicknameP4.text.toString()

                PreferenceUtility(requireContext()).apply {
                saveStringPreferences("nickname1", nicknamePlayer1)
                saveStringPreferences("nickname2", nicknamePlayer2)
                saveStringPreferences("nickname3", nicknamePlayer3)
                saveStringPreferences("nickname4", nicknamePlayer4)
                Snackbar.make(binding.root,"Information save", Snackbar.LENGTH_SHORT).show()
                }

        }
        return binding.root

        }
    override fun onResume() {
        super.onResume()

        PreferenceUtility(requireContext()).apply {
            binding.nicknameP1.setText(
                getStringPreferences("nickname1", "Player1").takeIf
                { it!!.isNotBlank() } ?: "Player1"
            )
            binding.nicknameP2.setText(
                getStringPreferences("nickname2", "Player2").takeIf
                { it!!.isNotBlank() } ?: "Player2"
            )
            binding.nicknameP3.setText(
                getStringPreferences("nickname3", "Player3").takeIf
                { it!!.isNotBlank() } ?: "Player3"
            )
            binding.nicknameP4.setText(
                getStringPreferences("nickname4", "Player4").takeIf
                { it!!.isNotBlank() } ?: "Player4"
            )
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}