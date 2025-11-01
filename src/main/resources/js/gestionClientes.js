/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
let clientes = [];
const form   = document.querySelector('form');
const tabla  = document.querySelector('tbody');

form.addEventListener('submit', guardarCliente);
tabla.addEventListener('click', accionesTabla);

function guardarCliente(e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(form));
  data.id = Date.now();
  clientes.push(data);
  form.reset();
  renderTabla();
  console.log('Cliente guardado:', data);
}

function renderTabla() {
  tabla.innerHTML = '';
  clientes.forEach(c => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${c.nombre}</td>
      <td>${c.apellido}</td>
      <td>${c.dni}</td>
      <td>${c.telefono}</td>
      <td>${c.correo_electronico}</td>
      <td>
        <button class="btn-edit" data-id="${c.id}">Editar</button>
        <button class="btn-delete" data-id="${c.id}">Eliminar</button>
        <button class="btn-ver-polizas" data-id="${c.dni}">Ver pólizas</button>
      </td>`;
    tabla.appendChild(tr);
  });
}

function accionesTabla(e) {
  const id = Number(e.target.dataset.id);
  if (e.target.classList.contains('btn-delete')) {
    clientes = clientes.filter(c => c.id !== id);
    renderTabla();
  }
  if (e.target.classList.contains('btn-edit')) {
    const c = clientes.find(x => x.id === id);
    if (c) {
      Object.keys(c).forEach(k => {
        const input = form.elements[k];
        if (input) input.value = c[k];
      });
      clientes = clientes.filter(x => x.id !== id);
    }
  }
}

// Array FAKE de pólizas (más adelante vendrán del backend)
const polizasFake = [
  {idCliente: 111, numPoliza: 'P-2025-1001', tipo: 'Vida', inicio: '2025-01-01', fin: '2026-01-01', estado: 'Vigente'},
  {idCliente: 111, numPoliza: 'P-2024-0955', tipo: 'Auto', inicio: '2024-06-15', fin: '2025-06-15', estado: 'Vencida'},
  {idCliente: 222, numPoliza: 'P-2025-1020', tipo: 'Hogar', inicio: '2025-03-10', fin: '2026-03-10', estado: 'Vigente'}
];


const historialSection = document.getElementById('historial');
const tblHist          = document.querySelector('#tblPolizasCliente tbody');
const btnCerrarHist    = document.getElementById('cerrarHistorial');
let clienteActualId    = null;   

// Delegación de eventos: botones "Ver pólizas"
document.querySelector('#tblClientes').addEventListener('click', e => {
  if (!e.target.classList.contains('btn-ver-polizas')) return;
  clienteActualId = e.target.dataset.id;          
  mostrarHistorial(clienteActualId);
});

btnCerrarHist.addEventListener('click', () => {
  historialSection.style.display = 'none';
});

function mostrarHistorial(dni) {
  const lista = polizasFake.filter(p => p.idCliente == dni);

  tblHist.innerHTML = '';
  lista.forEach(p => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${p.numPoliza}</td>
      <td>${p.tipo}</td>
      <td>${p.inicio}</td>
      <td>${p.fin}</td>
      <td>${p.estado}</td>`;
    tblHist.appendChild(tr);
  });

  historialSection.style.display = 'block';
  historialSection.scrollIntoView({behavior: 'smooth'});
}


