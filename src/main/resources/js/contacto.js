/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
const form = document.getElementById('contactoForm');
const btnDescargar = document.getElementById('btnDescargar');

form.addEventListener('submit', function (e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(form));
  alert('Mensaje enviado. Nos pondremos en contacto pronto.');
  console.log('Contacto:', data);
  form.reset();
});

btnDescargar.addEventListener('click', function () {
  const data = Object.fromEntries(new FormData(form));
  const txt = `COMPROBANTE DE CONTACTO - SeguriPlus
Fecha: ${new Date().toLocaleString('es-AR')}
Nombre: ${data.nombre}
Email: ${data.email}
Teléfono: ${data.telefono}
Asunto: ${data.asunto}
Mensaje: ${data.mensaje}
  `;
  const blob = new Blob([txt], {type: 'text/plain;charset=utf-8'});
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = `Contacto_${data.nombre.replace(/\s+/g, '_')}.txt`;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
});


