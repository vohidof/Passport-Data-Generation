package Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vohidov.passportdata.R
import com.vohidov.passportdata.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentHomeBinding.inflate(LayoutInflater.from(context))

        binding.btnBarchaFuqarolar.setOnClickListener {
            findNavController().navigate(R.id.barchaFuqarolarFragment)
        }
        binding.btnFuqaroQoshish.setOnClickListener {
            findNavController().navigate(R.id.fuqaroQoshishFragment)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.btnBarchaFuqarolar.setOnClickListener {
            findNavController().navigate(R.id.barchaFuqarolarFragment)
        }
        binding.btnFuqaroQoshish.setOnClickListener {
            findNavController().navigate(R.id.fuqaroQoshishFragment)
        }
    }
}