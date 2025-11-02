/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
const form = document.getElementById('registroForm');

form.addEventListener('submit', function (e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(form));

  if (data.password !== data.password2) {
    alert('Las contraseñas no coinciden');
    return;
  }


  console.log('Usuario registrado:', data);
  alert('Registro exitoso. Ahora puedes iniciar sesión.');
  form.reset();
});

