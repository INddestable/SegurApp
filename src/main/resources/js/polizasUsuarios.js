/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

const misPolizas = [
  {
    idPoliza: 'P-2025-1001',
    tipo: 'Vida',
    plan: 'Plan Oro',
    inicio: '2025-01-01',
    fin: '2026-01-01',
    estado: 'Vigente'
  },
  {
    idPoliza: 'P-2024-0955',
    tipo: 'Automóvil',
    plan: 'Básico',
    inicio: '2024-06-15',
    fin: '2025-06-15',
    estado: 'Vencida'
  },
  {
    idPoliza: 'P-2025-1020',
    tipo: 'Hogar',
    plan: 'Estándar',
    inicio: '2025-03-10',
    fin: '2026-03-10',
    estado: 'En renovación'
  }
];

/*
 * 2. REFERENCIAS DOM
 */
const tblBody      = document.querySelector('#tblMisPolizas tbody');
const filtroEstado = document.getElementById('filtroEstado');
const filtroTexto  = document.getElementById('filtroTexto');

/*
 * 3. EVENTOS
 */
filtroEstado.addEventListener('change', renderTabla);
filtroTexto.addEventListener('input', renderTabla);

/*
 * 4. FUNCIONES
 */
function renderTabla() {
  const est = filtroEstado.value;
  const txt = filtroTexto.value.toLowerCase();

  const filtradas = misPolizas.filter(p =>
    (!est || p.estado === est) &&
    (!txt || p.tipo.toLowerCase().includes(txt) || p.plan.toLowerCase().includes(txt))
  );

  tblBody.innerHTML = '';
  filtradas.forEach(p => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${p.idPoliza}</td>
      <td>${p.tipo}</td>
      <td>${p.plan}</td>
      <td>${p.inicio}</td>
      <td>${p.fin}</td>
      <td><span class="estado-${p.estado.toLowerCase()}">${p.estado}</span></td>
      <td><button class="btn-ver" data-id="${p.idPoliza}">Ver detalle</button></td>
    `;
    tblBody.appendChild(tr);
  });
}


tblBody.addEventListener('click', e => {
  if (e.target.classList.contains('btn-ver')) {
    const id = e.target.dataset.id;
    const p = misPolizas.find(x => x.idPoliza === id);
    if (p) alert(`Detalle de póliza\n\nTipo: ${p.tipo}\nPlan: ${p.plan}\nInicio: ${p.inicio}\nFin: ${p.fin}\nEstado: ${p.estado}`);
  }
});

renderTabla();

