package br.com.fiap.locaweb.screens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.fiap.locaweb.R
import br.com.fiap.locaweb.viewModel.UserViewModel

@Composable
fun TelaLogin(navController: NavHostController, userViewModel: UserViewModel) {
    // Função para validar o e-mail
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Estados para controlar o input e a validação
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }
    var senha by remember { mutableStateOf("") }
    var erroEmail by remember { mutableStateOf(false) }
    var erroSenha by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(15.dp))
        Text(
            text = "Bem Vindo a",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C343C)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(240.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Faça login para continuar",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C343C)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                // Atualize o estado de validação conforme o valor do e-mail
                isEmailValid = isValidEmail(it)
            },
            modifier = Modifier.width(370.dp),
            label = { Text(text = "E-mail") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            isError = !isEmailValid
        )

        if (!isEmailValid) {
            Text(
                text = "E-mail inválido",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            modifier = Modifier.width(370.dp),
            label = { Text(text = "Senha") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        if (erroSenha) {
            Text(
                text = "A senha é obrigatória!",
                modifier = Modifier.width(395.dp),
                fontSize = 14.sp,
                color = Color.Red,
                textAlign = TextAlign.End
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Não possui login?",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2C343C)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFFF30B44))
        ) {
            Text(text = "Cadastrar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isEmpty()) erroEmail = true else erroEmail = false
                if (senha.isEmpty()) erroSenha = true else erroSenha = false

                if (!erroEmail && !erroSenha) {
                    // Chamada ao ViewModel para logar
                    userViewModel.login(email, senha)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFFF30B44))
        ) {
            Text(text = "Entrar", color = Color.White)
        }
    }

    // Observa o estado do usuário logado no ViewModel
    userViewModel.usuarioLogado?.let { usuario ->
        // Se o login for bem-sucedido, navega para a próxima tela
        navController.navigate("menuEmail")
    }
}