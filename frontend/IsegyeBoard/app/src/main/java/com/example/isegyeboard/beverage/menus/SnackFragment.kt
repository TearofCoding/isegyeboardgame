package com.example.isegyeboard.beverage.menus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.isegyeboard.R
import com.example.isegyeboard.beverage.BeverageAdapter
import com.example.isegyeboard.beverage.BeverageClass
import com.example.isegyeboard.beverage.SnackViewModel
import com.example.isegyeboard.beverage.cart.CartAdapter
import com.example.isegyeboard.databinding.FragmentSnackBinding
import kotlinx.coroutines.launch

class SnackFragment : Fragment() {

    private lateinit var binding: FragmentSnackBinding

    private val viewModel: SnackViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter
    private lateinit var beverageAdapter: BeverageAdapter
    private lateinit var snackList: List<BeverageClass>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSnackBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartAdapter = CartAdapter()
        beverageAdapter = BeverageAdapter(requireContext(), emptyList(), cartAdapter) // 초기에 빈 리스트로 어댑터 생성
        binding.snackRV.adapter = beverageAdapter
        binding.snackRV.layoutManager = GridLayoutManager(requireContext(), 4)

        viewModel.getCurrentMenuList()

        lifecycleScope.launch {
            viewModel.snackMenuList.observe(viewLifecycleOwner) { menuList ->
                beverageAdapter.updateData(menuList) // 데이터 업데이트
                snackList = menuList
            }
        }

        // to non-coffee 버튼
        binding.buttonDrink.setOnClickListener{
            it.findNavController().navigate(R.id.action_snackFragment_to_drinkFragment)
        }

        // to snack 버튼
        binding.buttonCoffee.setOnClickListener{
            it.findNavController().navigate(R.id.action_snackFragment_to_coffeFragment)
        }
    }
}
