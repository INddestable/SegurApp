/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

let seguros = [
  {
    id: 1,
    tipo: 'Vida',
    aseguradora: 'SegurosXYZ',
    plan: 'Plan Oro',
    cobertura: 'Muerte natural o accidental',
    beneficios: 'Asistencia funeraria, capital para familia',
    exclusiones: 'Suicidio en primer año',
    condiciones: 'Contrato mínimo 1 año',
    costo: 500,
    duracion: 1
  },
  {
    id: 2,
    tipo: 'Automóvil',
    aseguradora: 'AutoSeguro',
    plan: 'Básico',
    cobertura: 'Daños a terceros',
    beneficios: 'Servicio de grúa',
    exclusiones: 'Conducción bajo efectos del alcohol',
    condiciones: 'Vehículo menor a 15 años',
    costo: 350,
    duracion: 1
  }
];

/*
 * 2. REFERENCIAS DOM
 */
const form         = document.getElementById('seguroForm');
const tblBody      = document.querySelector('#tblSeguros tbody');
const btnCancelEdit= document.getElementById('btnCancelEdit');
const busquedaForm = document.getElementById('busquedaForm');
const btnLimpiar   = document.getElementById('btnLimpiar');
const modal        = document.getElementById('modalDetalle');
const detContent   = document.getElementById('detalleContent');
const btnCerrarModal=document.getElementById('btnCerrarModal');
const tituloForm   = document.getElementById('tituloForm');

/*
 * 3. EVENTOS
 */
form.addEventListener('submit', guardarSeguro);
btnCancelEdit.addEventListener('click', cancelarEdicion);
busquedaForm.addEventListener('submit', buscarAvanzado);
btnLimpiar.addEventListener('click', limpiarBusqueda);
btnCerrarModal.addEventListener('click', () => modal.close());
tblBody.addEventListener('click', accionesTabla);


renderTabla();

/*
 * 4. FUNCIONES
 */
// ---------- CRUD ----------
function guardarSeguro(e) {
  e.preventDefault();
  const data = Object.fromEntries(new FormData(form));

  const idEdit = Number(data.id);
  if (idEdit) { // edición
    const idx = seguros.findIndex(s => s.id === idEdit);
    if (idx !== -1) seguros[idx] = {id: idEdit, ...data};
  } else {      // alta
    const nuevoId = seguros.length ? Math.max(...seguros.map(s => s.id)) + 1 : 1;
    seguros.push({id: nuevoId, ...data});
  }

  form.reset();
  cancelarEdicion();
  renderTabla();
  console.log('Seguro guardado:', data);
}

function cancelarEdicion() {
  form.id.value = '';
  btnCancelEdit.style.display = 'none';
  tituloForm.textContent = 'Registrar Nuevo Seguro';
}


function renderTabla(filtrados = seguros) {
  tblBody.innerHTML = '';
  filtrados.forEach(s => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${s.tipo}</td>
      <td>${s.aseguradora}</td>
      <td>${s.plan}</td>
      <td>${s.cobertura}</td>
      <td>$${Number(s.costo).toFixed(2)}</td>
      <td>${s.duracion} año(s)</td>
      <td>
        <button class="btn-small btn-ver" data-id="${s.id}">Ver</button>
        <button class="btn-small btn-edit" data-id="${s.id}">Editar</button>
        <button class="btn-small btn-delete" data-id="${s.id}">Eliminar</button>
      </td>`;
    tblBody.appendChild(tr);
  });
}


function buscarAvanzado(e) {
  e.preventDefault();
  const fd = new FormData(busquedaForm);
  const tipo = fd.get('bTipo').toLowerCase();
  const cob  = fd.get('bCobertura').toLowerCase();
  const min  = parseFloat(fd.get('bCostoMin')) || 0;
  const max  = parseFloat(fd.get('bCostoMax')) || Infinity;
  const aseg = fd.get('bAseguradora').toLowerCase();

  const filtrados = seguros.filter(s =>
    (!tipo || s.tipo.toLowerCase().includes(tipo)) &&
    (!cob  || s.cobertura.toLowerCase().includes(cob)) &&
    (s.costo >= min && s.costo <= max) &&
    (!aseg || s.aseguradora.toLowerCase().includes(aseg))
  );
  renderTabla(filtrados);
}

function limpiarBusqueda() {
  busquedaForm.reset();
  renderTabla();
}


function accionesTabla(e) {
  const id = Number(e.target.dataset.id);
  if (e.target.classList.contains('btn-delete')) {
    if (confirm('¿Confirma que desea eliminar este seguro?')) {
      seguros = seguros.filter(s => s.id !== id);
      renderTabla();
    }
  }
  if (e.target.classList.contains('btn-edit')) {
    const s = seguros.find(x => x.id === id);
    if (s) cargarSeguroEnForm(s);
  }
  if (e.target.classList.contains('btn-ver')) {
    const s = seguros.find(x => x.id === id);
    if (s) mostrarDetalle(s);
  }
}

function cargarSeguroEnForm(s) {
  Object.keys(s).forEach(k => {
    const campo = form.elements[k];
    if (campo) campo.value = s[k];
  });
  tituloForm.textContent = 'Modificar Seguro';
  btnCancelEdit.style.display = 'inline-block';
  form.id.value = s.id;
}

function mostrarDetalle(s) {
  detContent.textContent = `
Tipo: ${s.tipo}
Aseguradora: ${s.aseguradora}
Plan: ${s.plan}
Costo: $${Number(s.costo).toFixed(2)} / año (${s.duracion} año(s))

COBERTURA:
${s.cobertura}

BENEFICIOS:
${s.beneficios}

EXCLUSIONES:
${s.exclusiones}

CONDICIONES:
${s.condiciones}
  `;
  modal.showModal();
}


