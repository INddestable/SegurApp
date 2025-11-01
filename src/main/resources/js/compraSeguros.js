/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
const productos = [
  {
    id: 1,
    tipo: 'Vida',
    plan: 'Plan Oro',
    aseguradora: 'SegurosXYZ',
    costo: 500,
    duracion: 1,
    cobertura: 'Muerte natural o accidental',
    img: 'https://images.unsplash.com/photo-1607863682858-30b49dc5c6ec?auto=format&fit=crop&w=600&q=80'
  },
  {
    id: 2,
    tipo: 'Automóvil',
    plan: 'Básico',
    aseguradora: 'AutoSeguro',
    costo: 350,
    duracion: 1,
    cobertura: 'Daños a terceros',
    img: 'https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?auto=format&fit=crop&w=600&q=80'
  },
  {
    id: 3,
    tipo: 'Hogar',
    plan: 'Estándar',
    aseguradora: 'HogarSeguro',
    costo: 280,
    duracion: 1,
    cobertura: 'Incendio, robo, daños',
    img: 'https://images.unsplash.com/photo-1564013799919-ab600027ffc6?auto=format&fit=crop&w=600&q=80'
  }
];

let carrito = []; // [{id, tipo, plan, costo, cantidad}]


 /* 2. REFERENCIAS DOM */

const listaProductos  = document.getElementById('listaProductos');
const tblBody         = document.querySelector('#tblCarrito tbody');
const totalCarrito    = document.getElementById('totalCarrito');
const btnFinalizar    = document.getElementById('btnFinalizarCompra');

/*
 * 3. EVENTOS
 */
btnFinalizar.addEventListener('click', finalizarCompra);

/*
 * 4. FUNCIONES */
function renderProductos() {
  listaProductos.innerHTML = '';
  productos.forEach(p => {
    const card = document.createElement('div');
    card.className = 'producto-card';
    card.innerHTML = `
      <img src="${p.img}" alt="${p.tipo}">
      <div class="producto-info">
        <h3>${p.tipo} - ${p.plan}</h3>
        <p>${p.cobertura}</p>
        <p class="precio">$${p.costo}/año</p>
      </div>
      <div class="producto-footer">
        <span>${p.aseguradora}</span>
        <button class="btn-agregar" data-id="${p.id}">Agregar</button>
      </div>
    `;
    listaProductos.appendChild(card);
  });
}

function renderCarrito() {
  tblBody.innerHTML = '';
  let total = 0;
  carrito.forEach(item => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${item.tipo}</td>
      <td>${item.plan}</td>
      <td>$${item.costo}</td>
      <td>${item.cantidad}</td>
      <td>$${(item.costo * item.cantidad).toFixed(2)}</td>
      <td><button class="btn-quitar" data-id="${item.id}">Quitar</button></td>
    `;
    tblBody.appendChild(tr);
    total += item.costo * item.cantidad;
  });
  totalCarrito.textContent = total.toFixed(2);
}

// Delegar acciones sobre productos y carrito
listaProductos.addEventListener('click', e => {
  if (e.target.classList.contains('btn-agregar')) {
    const id = Number(e.target.dataset.id);
    const prod = productos.find(p => p.id === id);
    if (!prod) return;
    const existe = carrito.find(c => c.id === id);
    if (existe) existe.cantidad++;
    else carrito.push({...prod, cantidad: 1});
    renderCarrito();
  }
});

tblBody.addEventListener('click', e => {
  if (e.target.classList.contains('btn-quitar')) {
    const id = Number(e.target.dataset.id);
    carrito = carrito.filter(c => c.id !== id);
    renderCarrito();
  }
});

function finalizarCompra() {
  if (carrito.length === 0) {
    alert('El carrito está vacío');
    return;
  }
  const total = parseFloat(totalCarrito.textContent);
  const txt = `COMPROBANTE DE COMPRA - SeguriPlus
Fecha: ${new Date().toLocaleString('es-AR')}
Productos:
${carrito.map(c => `- ${c.tipo} ${c.plan} x${c.cantidad} = $${(c.costo * c.cantidad).toFixed(2)}`).join('\n')}
TOTAL: $${total.toFixed(2)}
  `;
  const blob = new Blob([txt], {type: 'text/plain;charset=utf-8'});
  const url = URL.createObjectURL(blob);
  const a = document.createElement('a');
  a.href = url;
  a.download = `Compra_${Date.now()}.txt`;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);

  alert('Compra finalizada. Gracias por tu confianza.');
  carrito = [];
  renderCarrito();
}


renderProductos();
renderCarrito();


