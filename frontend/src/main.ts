type Product = { id?: number; name: string; price: number; description?: string };

const API = (import.meta as any).env?.VITE_API_BASE_URL || '/api';
const app = document.getElementById('app')!;

function view(items: Product[]) {
  app.innerHTML = `
    <h2>CRUD Productos</h2>
    <div style="display:flex;gap:8px;margin-bottom:12px">
      <input id="name" placeholder="Nombre"/>
      <input id="price" type="number" placeholder="Precio" value="0"/>
      <button id="add">Agregar</button>
    </div>
    <ul>${items.map(p => `<li>${p.name} — $${p.price}</li>`).join('')}</ul>
  `;

  document.getElementById('add')!.addEventListener('click', async () => {
    const name = (document.getElementById('name') as HTMLInputElement).value;
    const price = Number((document.getElementById('price') as HTMLInputElement).value);
    const res = await fetch(`${API}/products`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, price, description: '' })
    });
    if (!res.ok) {
      const t = await res.text();
      alert('Error al crear: ' + t);
      return;
    }
    await load();
  });
}

async function load() {
  const res = await fetch(`${API}/products`);
  if (!res.ok) {
    app.innerHTML = `<p style="color:red">Error: ${res.status} ${res.statusText}</p>`;
    return;
  }
  const data = await res.json() as Product[];
  view(data);
}

load();
