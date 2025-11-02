/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

let polizas = [];                
let clienteSeleccionado = null;  


const clientesFake = [
  {dni: '12345678', nombre: 'Juan Pérez'},
  {dni: '87654321', nombre: 'Ana Gómez'},
  {dni: '11223344', nombre: 'Carlos Ruiz'}
];


polizas = [
  {
    id: 1,
    cliente: '12345678 - Juan Pérez',
    tipoSeguro: 'Vida',
    plan: 'Plan Oro',
    inicio: '2025-01-01',
    fin: '2026-01-01',
    prima: 500,
    estado: 'Vigente',
    motivoCancel: '',
    obsCancel: ''
  },
  {
    id: 2,
    cliente: '87654321 - Ana Gómez',
    tipoSeguro: 'Automóvil',
    plan: 'Básico',
    inicio: '2024-06-15',
    fin: '2025-06-15',
    prima: 350,
    estado: 'Vencida',
    motivoCancel: '',
    obsCancel: ''
  }
];

/*
 * 2. REFERENCIAS DOM
 */
const form          = document.getElementById('polizaForm');
const tblBody       = document.querySelector('#tblPolizas tbody');
const btnCancelEdit = document.getElementById('btnCancelEdit');
const btnCert       = document.getElementById('btnCert');
const cancelDiv     = document.getElementById('cancelFields');
const estadoSelect  = form.estado;
const busquedaInput = document.getElementById('busqueda');
const filtroEstado  = document.getElementById('filtroEstado');
const tituloForm    = document.getElementById('tituloForm');

/*
 * 3. EVENTOS
 */
form.addEventListener('submit', guardarPoliza);
btnCancelEdit.addEventListener('click', cancelarEdicion);
estadoSelect.addEventListener('change', mostrarCamposCancel);
btnCert.addEventListener('click', descargarCertificado);
busquedaInput.addEventListener('input', renderTabla);
filtroEstado.addEventListener('change', renderTabla);
tblBody.addEventListener('click', accionesTabla);


rellenarClientesDatalist();
renderTabla();

/*
 * 4. FUNCIONES
 */
function guardarPoliza(e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(form));


  const idEdit = Number(data.id);
  if (idEdit) { 
    const idx = polizas.findIndex(p => p.id === idEdit);
    if (idx !== -1) polizas[idx] = {id: idEdit, ...data};
  } else {      
    const nuevoId = polizas.length ? Math.max(...polizas.map(p => p.id)) + 1 : 1;
    polizas.push({id: nuevoId, ...data});
  }

  form.reset();
  cancelarEdicion();
  renderTabla();
  console.log('Póliza guardada:', data);
}

function cancelarEdicion() {
  form.id.value = '';
  btnCancelEdit.style.display = 'none';
  tituloForm.textContent = 'Registrar Nueva Póliza';
}


function mostrarCamposCancel() {
  if (estadoSelect.value === 'Cancelada') {
    cancelDiv.style.display = 'block';
  } else {
    cancelDiv.style.display = 'none';
    form.motivoCancel.value = '';
    form.obsCancel.value = '';
  }
}


function renderTabla() {
  const txt = busquedaInput.value.toLowerCase();
  const est = filtroEstado.value;

  const filtradas = polizas.filter(p => {
    const coincideTexto = !txt ||
      p.cliente.toLowerCase().includes(txt) ||
      p.tipoSeguro.toLowerCase().includes(txt) ||
      p.plan.toLowerCase().includes(txt);
    const coincideEstado = !est || p.estado === est;
    return coincideTexto && coincideEstado;
  });

  tblBody.innerHTML = '';
  filtradas.forEach(p => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${p.id}</td>
      <td>${p.cliente}</td>
      <td>${p.tipoSeguro}</td>
      <td>${p.plan}</td>
      <td>${p.inicio}</td>
      <td>${p.fin}</td>
      <td>$${Number(p.prima).toFixed(2)}</td>
      <td><span class="estado-${p.estado.toLowerCase()}">${p.estado}</span></td>
      <td>
        <button class="btn-edit" data-id="${p.id}">Editar</button>
        <button class="btn-delete" data-id="${p.id}">Eliminar</button>
        <button class="btn-cert" data-id="${p.id}">Certificado</button>
      </td>`;
    tblBody.appendChild(tr);
  });
}


function accionesTabla(e) {
  const id = Number(e.target.dataset.id);
  if (e.target.classList.contains('btn-delete')) {
    if (confirm('¿Confirma que desea eliminar esta póliza?')) {
      polizas = polizas.filter(p => p.id !== id);
      renderTabla();
    }
  }
  if (e.target.classList.contains('btn-edit')) {
    const p = polizas.find(x => x.id === id);
    if (p) cargarPolizaEnForm(p);
  }
  if (e.target.classList.contains('btn-cert')) {
    clienteSeleccionado = polizas.find(p => p.id === id);
    btnCert.disabled = !clienteSeleccionado;
  }
}

function cargarPolizaEnForm(p) {
  Object.keys(p).forEach(k => {
    const campo = form.elements[k];
    if (campo) campo.value = p[k];
  });
  mostrarCamposCancel();
  tituloForm.textContent = 'Modificar Póliza';
  btnCancelEdit.style.display = 'inline-block';
  form.id.value = p.id;
}


function descargarCertificado() {
  if (!clienteSeleccionado) return;

  const {cliente, tipoSeguro, plan, inicio, fin, prima, estado} = clienteSeleccionado;
  const texto = `
CERTIFICADO DE PÓLIZA
====================
Póliza N°: ${clienteSeleccionado.id}
Cliente: ${cliente}
Tipo: ${tipoSeguro}
Plan: ${plan}
Vigencia: ${inicio} al ${fin}
Prima anual: $${Number(prima).toFixed(2)}
Estado: ${estado}
Generado el: ${new Date().toLocaleString('es-AR')}
  `;


  const blob = new Blob([texto], {type: 'text/plain;charset=utf-8'});
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = `Certificado_Poliza_${clienteSeleccionado.id}.txt`;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}


function rellenarClientesDatalist() {
  const dl = document.getElementById('listaClientes');
  clientesFake.forEach(c => {
    const opt = document.createElement('option');
    opt.value = `${c.dni} - ${c.nombre}`;
    dl.appendChild(opt);
  });
}


