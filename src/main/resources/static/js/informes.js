/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

// Ventas
const ventas = [
  {tipo:'Vida',     cliente:'Juan Pérez', region:'Norte', fecha:'2025-01-15', monto:1200, estado:'Vigente'},
  {tipo:'Automóvil',cliente:'Ana Gómez',  region:'Sur',   fecha:'2025-02-20', monto: 850, estado:'Vigente'},
  {tipo:'Hogar',    cliente:'Carlos R',   region:'Este',  fecha:'2025-03-10', monto: 600, estado:'Cancelada'},
  {tipo:'Vida',     cliente:'Laura S',    region:'Oeste', fecha:'2025-04-05', monto:1500, estado:'Vigente'},
  {tipo:'Salud',    cliente:'Otro',       region:'Norte', fecha:'2025-05-12', monto: 700, estado:'Vencida'},
  {tipo:'Vida',     cliente:'Más vida',   region:'Sur',   fecha:'2025-06-18', monto:1300, estado:'Vigente'}
];

// Pagos (para atrasados)
const pagos = [
  {cliente:'Juan Pérez', fechaPago:'2025-10-01', estado:'Completado'},
  {cliente:'Ana Gómez',  fechaPago:'2025-08-15', estado:'Pendiente'}
];

// Pólizas (para renovaciones y cancelaciones)
const polizas = [
  {cliente:'Juan Pérez', estado:'Vigente'},
  {cliente:'Ana Gómez',  estado:'En renovación'},
  {cliente:'Carlos R',   estado:'Cancelada'},
  {cliente:'Laura S',    estado:'Vigente'}
];

/*
 * 2. REFERENCIAS DOM
 */
const filtrosForm   = document.getElementById('filtrosForm');
const tblBody       = document.querySelector('#tblResultados tbody');
const btnDescTXT    = document.getElementById('btnDescargarTXT');
const stsRenov      = document.getElementById('stsRenov');
const stsCancel     = document.getElementById('stsCancel');
const stsAtraso     = document.getElementById('stsAtraso');
const stsTop        = document.getElementById('stsTop');
const totalVentas   = document.getElementById('totalVentas');
const ctx           = document.getElementById('chartVentas').getContext('2d');

let chart = null; // instancia Chart.js

/*
 * 3. EVENTOS
 */
filtrosForm.addEventListener('submit', generarInforme);
btnDescTXT.addEventListener('click', descargarTXT);

// Inicializar
calcularStatsRapidas();
generarInforme(new Event('submit'));

/*
 * 4. FUNCIONES
 */
// ---------- GENERAR INFORME ----------
function generarInforme(e) {
  e.preventDefault();
  const fd = new FormData(filtrosForm);
  const tipo   = fd.get('tipoSeguro');
  const cliente= fd.get('cliente');
  const region = fd.get('region');
  const desde  = fd.get('fechaDesde');
  const hasta  = fd.get('fechaHasta');

  const filtradas = ventas.filter(v => {
    return (!tipo   || v.tipo === tipo) &&
           (!cliente|| v.cliente === cliente) &&
           (!region || v.region === region) &&
           (!desde  || v.fecha >= desde) &&
           (!hasta  || v.fecha <= hasta);
  });

  tblBody.innerHTML = '';
  let sum = 0;
  filtradas.forEach(v => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${v.tipo}</td>
      <td>${v.cliente}</td>
      <td>${v.region}</td>
      <td>${v.fecha}</td>
      <td>${v.monto.toFixed(2)}</td>
      <td>${v.estado}</td>`;
    tblBody.appendChild(tr);
    sum += v.monto;
  });
  totalVentas.textContent = sum.toFixed(2);

  actualizarGrafico(filtradas);
}

// ---------- ESTADÍSTICAS RÁPIDAS ----------
function calcularStatsRapidas() {
  // Renovaciones
  const renov = polizas.filter(p => p.estado === 'En renovación').length;
  stsRenov.textContent = renov;

  // Cancelaciones
  const cancel = polizas.filter(p => p.estado === 'Cancelada').length;
  stsCancel.textContent = cancel;

  // Pagos atrasados (ejemplo simple: más de 30 días)
  const hoy = new Date();
  const atrasos = pagos.filter(p => {
    const dias = (hoy - new Date(p.fechaPago)) / (1000 * 60 * 60 * 24);
    return p.estado === 'Pendiente' && dias > 30;
  }).length;
  stsAtraso.textContent = atrasos;

  // Más vendido
  const conteo = {};
  ventas.forEach(v => {
    conteo[v.tipo] = (conteo[v.tipo] || 0) + 1;
  });
  const topTipo = Object.entries(conteo).sort((a, b) => b[1] - a[1])[0];
  stsTop.textContent = topTipo ? `${topTipo[0]} (${topTipo[1]})` : '-';
}

function actualizarGrafico(datos) {
  const conteo = {};
  datos.forEach(v => {
    conteo[v.tipo] = (conteo[v.tipo] || 0) + v.monto;
  });

  if (chart) chart.destroy();
  chart = new Chart(ctx, {
    type: 'bar',
    data: {
      labels: Object.keys(conteo),
      datasets: [{
        label: 'Ventas ($)',
        data: Object.values(conteo),
        backgroundColor: '#d84315'
      }]
    },
    options: {
      responsive: true,
      plugins: {legend: {display: false }},
      scales: {y: {beginAtZero: true}}
    }
  });
}

function descargarTXT() {
  const filas = Array.from(tblBody.querySelectorAll('tr'));
  let txt = 'INFORME DE VENTAS\n=================\n\n';
  filas.forEach(r => {
    const c = Array.from(r.querySelectorAll('td')).map(td => td.textContent);
    txt += `${c.join('\t')}\n`;
  });
  txt += `\nTotal ventas: $${totalVentas.textContent}\n`;
  const blob = new Blob([txt], {type: 'text/plain;charset=utf-8'});
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = `Informe_Ventas_${new Date().toISOString().slice(0,10)}.txt`;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}


