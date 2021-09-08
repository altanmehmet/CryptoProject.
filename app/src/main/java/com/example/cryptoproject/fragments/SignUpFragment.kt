package com.example.cryptoproject.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.cryptoproject.R
import com.example.cryptoproject.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SignUpFragment : Fragment() {
        private lateinit var emailS: String;
        private lateinit var passwordS: String;
        private lateinit var nameS: String
        private lateinit var db: FirebaseFirestore
        private var auth: FirebaseAuth? = null
        private var _binding: FragmentSignUpBinding? = null
        private val binding get() = _binding!!
        override fun onCreate(savedInstanceState: Bundle?) {
            auth = FirebaseAuth.getInstance()
            super.onCreate(savedInstanceState)

        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentSignUpBinding.inflate(inflater, container, false)
            val view = binding.root
            binding.signbutton.setOnClickListener {
                emailS = binding.email.text.toString();
                passwordS = binding.password.text.toString();
                nameS = binding.name.text.toString();
                SignUp(emailS, passwordS)
                val fragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                val fragment = LoginFragment()
                fragmentTransaction.replace(R.id.fragment_container, fragment)
                fragmentTransaction.commit()

            }
            binding.signin.setOnClickListener {
                val fragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                val fragment = LoginFragment()
                fragmentTransaction.replace(R.id.fragment_container, fragment)
                fragmentTransaction.commit()
            }
            return view
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun SignUp(mail: String, sifre: String) {

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail, sifre)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(test: Task<AuthResult>) {
                        if (test.isSuccessful) {
                            FirebaseAuth.getInstance().signOut()

                        }
                    }
                })

        }
    }

