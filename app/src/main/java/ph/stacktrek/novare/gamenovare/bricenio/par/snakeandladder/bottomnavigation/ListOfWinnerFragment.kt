package ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.bottomnavigation
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.adapters.ListOfWinnerAdapter
import ph.stacktrek.novare.gamenovare.bricenio.par.snakeandladder.databinding.FragmentListOfWinnerBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding:FragmentListOfWinnerBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ListOfWinnerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListOfWinnerFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null


    private var _binding: FragmentListOfWinnerBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentListOfWinnerBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val history = winners()
        val winnerAdapter = ListOfWinnerAdapter(history)
        binding.winnersList.layoutManager = LinearLayoutManager(requireContext())
        binding.winnersList.adapter = winnerAdapter

    }
    //Get Names Of Winners
    private fun winners(): List <String> {
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val winnersJson = preferences.getString("winners", "[]") ?: "[]"
        val winners = Gson().fromJson(winnersJson, object : TypeToken<List<String>>() {}.type)
            as MutableList<String>

        return if (winners.size > 5){
            winners.takeLast(5).reversed()
        }else{
            winners.reversed()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListOfWinnerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListOfWinnerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}