package com.example.cryptoproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import com.example.cryptoproject.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.SharedPreferences
import android.text.Editable
import kotlinx.android.synthetic.main.fragment_login.*

//mehmetturkmen200@hotmail.com




class LoginFragment : Fragment() {
    private lateinit var toolbar: Toolbar;
    lateinit var preferences : SharedPreferences
    private lateinit var auth : FirebaseAuth;
    private lateinit var emailS : String
    private lateinit var passwordS : String
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferences = requireActivity().getSharedPreferences("com.example.cryptoproject",Context.MODE_PRIVATE)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        val savedString: String? = preferences.getString("stringValue", "Kayıt Yok")
        val savedPassword : String? = preferences.getString("intValue","bos")
        val savedChecked: Boolean = preferences.getBoolean("isChecked", false)
        if(savedChecked){
            binding.email.text =Editable.Factory.getInstance().newEditable(savedString)
            binding.password.text = Editable.Factory.getInstance().newEditable(savedPassword.toString())
            binding.checkBox.isChecked = savedChecked
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.signupButton.setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val fragment = SignUpFragment()
            fragmentTransaction.replace(R.id.fragment_container, fragment)
            fragmentTransaction.commit()
        }

        binding.loginButton.setOnClickListener {
            emailS = binding.email.text.toString()
            var stringValue = binding.email.text
            var intValue = binding.password.text.toString()
            passwordS = binding.password.text.toString()
            var isChecked = binding.checkBox.isChecked()
            auth.signInWithEmailAndPassword(emailS, passwordS)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val editor: SharedPreferences.Editor =
                            preferences.edit()
                        editor.putString("stringValue", stringValue.toString())
                        editor.putString("intValue",intValue)
                        editor.putBoolean("isChecked", isChecked)
                        editor.commit()

                        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                        val fragment = ListeFragment()
                        fragmentTransaction.replace(R.id.fragment_container, fragment)
                        fragmentTransaction.commit()
                    } else {
                        Toast.makeText(requireContext(), "password or email is incorrect!", Toast.LENGTH_SHORT).show()
                        task.exception!!.message?.let { it1 -> Log.e("Giriş Hatası", it1) }
                    }
                }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    }
