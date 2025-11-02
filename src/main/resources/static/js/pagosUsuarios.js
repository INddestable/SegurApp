/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

const misPolizas = [
  {idPoliza: 'P-2025-1001', tipo: 'Vida', plan: 'Plan Oro', costo: 500, estado: 'Pendiente'},
  {idPoliza: 'P-2024-0955', tipo: 'Auto', plan: 'Básico',  costo: 350, estado: 'Pagada'}
];

const pagosHechos = [
  {idPoliza: 'P-2025-1001', fechaPago: '2025-10-01', formaPago: 'Tarjeta débito', monto: 500, estado: 'Completado'},
  {idPoliza: 'P-2024-0955', fechaPago: '2025-09-15', formaPago: 'Transferencia', monto: 350, estado: 'Completado'}
];

/*
 * 2. REFERENCIAS DOM
*/
const selPoliza     = document.getElementById('selPoliza');
const inputMonto    = document.getElementById('monto');
const tblBody       = document.querySelector('#tblPagosPoliza tbody');
const form          = document.getElementById('pagoForm');
const btnComprobante= document.getElementById('btnComprobante');

/*
 * 3. EVENTOS
 */
selPoliza.addEventListener('change', cargarDatosPoliza);
form.addEventListener('submit', realizarPago);
btnComprobante.addEventListener('click', descargarComprobante);

/*
 * 4. FUNCIONES
 */
// Rellenar selector de pólizas
function llenarSelector() {
  selPoliza.innerHTML = '<option value="">Elija una póliza...</option>';
  misPolizas.forEach(p => {
    const opt = document.createElement('option');
    opt.value = p.idPoliza;
    opt.textContent = `${p.idPoliza} - ${p.tipo} (${p.estado})`;
    opt.dataset.costo = p.costo;
    opt.dataset.estado = p.estado;
    selPoliza.appendChild(opt);
  });
}

function cargarDatosPoliza() {
  const opt = selPoliza.selectedOptions[0];
  if (!opt.value) {
    inputMonto.value = '';
    tblBody.innerHTML = '';
    btnComprobante.disabled = true;
    return;
  }
  const costo = parseFloat(opt.dataset.costo);
  const estado = opt.dataset.estado;
  inputMonto.value = costo.toFixed(2);

  // Filtrar pagos de esa póliza
  const filtrados = pagosHechos.filter(p => p.idPoliza === opt.value);
  tblBody.innerHTML = '';
  filtrados.forEach(p => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${p.idPoliza}</td>
      <td>${p.fechaPago}</td>
      <td>${p.formaPago}</td>
      <td>$${p.monto}</td>
      <td>${p.estado}</td>
    `;
    tblBody.appendChild(tr);
  });
  btnComprobante.disabled = false;
}


function realizarPago(e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(form));
  if (!data.poliza) {
    alert('Seleccione una póliza');
    return;
  }
  // Agregar pago al array
  pagosHechos.push({
    idPoliza: data.poliza,
    fechaPago: data.fechaPago,
    formaPago: data.formaPago,
    monto: parseFloat(data.monto),
    estado: 'Completado'
  });
  alert('Pago registrado con éxito');
  form.reset();
  llenarSelector();
  cargarDatosPoliza();
}


function descargarComprobante() {
  const polizaId = selPoliza.value;
  const pago = pagosHechos.filter(p => p.idPoliza === polizaId);
  if (!pago.length) return;

  const txt = `COMPROBANTE DE PAGO - SeguriPlus
Fecha generación: ${new Date().toLocaleString('es-AR')}
Póliza: ${polizaId}
Pagos realizados:
${pago.map(p => `- ${p.fechaPago} | ${p.formaPago} | $${p.monto.toFixed(2)} | ${p.estado}`).join('\n')}
TOTAL PAGADO: $${pago.reduce((sum, p) => sum + p.monto, 0).toFixed(2)}
  `;
  const blob = new Blob([txt], {type: 'text/plain;charset=utf-8'});
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = `ComprobantePago_${polizaId}.txt`;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}

llenarSelector();


