/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
const form = document.getElementById('ayudaForm');
const btnDescargar = document.getElementById('btnDescargarFAQ');

form.addEventListener('submit', function (e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(form));
  alert('Consulta enviada. Nos pondremos en contacto pronto.');
  console.log('Ayuda:', data);
  form.reset();
});

btnDescargar.addEventListener('click', function () {
  const txt = `FAQ - SeguriPlus
================

1. ¿Cómo contrato un seguro?
   Ir a Seguros → Seleccionar tipo/plan → Contratar.

2. ¿Dónde consulto mis pólizas?
   Módulo Pólizas en el menú lateral.

3. ¿Cómo pago mi póliza?
   Módulo Pagos → Registrar pago → Descargar comprobante.

4. ¿Cómo contacto a soporte?
   Usar el formulario de Ayuda o ir a Contacto.

5. ¿Cómo genero informes?
   Módulo Informes → Elegir filtros → Generar/Descargar.

Fecha: ${new Date().toLocaleString('es-AR')}
  `;
  const blob = new Blob([txt], {type: 'text/plain;charset=utf-8'});
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = 'FAQ_SeguriPlus.txt';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
});


