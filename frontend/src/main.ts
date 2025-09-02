type School = { id:number; name:string; city:string; type:'FISCAL'|'FISCOMISIONAL'|'PARTICULAR' };
type Career = { id:number; name:string };
type Quote = { base:number; discountPct:number; total:number; schoolType:string; career:string };

const API = (import.meta as any).env?.VITE_API_BASE_URL || '/api';
const app = document.getElementById('app')!;

const state = { schools: [] as School[], careers: [] as Career[], quote: null as Quote|null };

function html(strings:TemplateStringsArray, ...expr:any[]){ return strings.map((s,i)=>s+(expr[i]??'')).join(''); }

async function loadData(){
  const [s,c] = await Promise.all([
    fetch(`${API}/colegios`).then(r=>r.json()),
    fetch(`${API}/carreras`).then(r=>r.json())
  ]);
  state.schools = s; state.careers = c; renderForm();
}

function renderForm(){
  app.innerHTML = html`
    <h2>Registrar inscripción</h2>
    <div style="display:grid;gap:8px;max-width:520px;">
      <input id="name" placeholder="Nombre" />
      <input id="cedula" placeholder="Cédula (10 dígitos)" />
      <input id="email" placeholder="Correo electrónico" />
      <select id="schoolId">
        <option value="">Selecciona colegio</option>
        ${state.schools.map(s=>`<option value="${s.id}">${s.name} — ${s.city} (${s.type})</option>`).join('')}
      </select>
      <select id="careerId">
        <option value="">Selecciona carrera</option>
        ${state.careers.map(c=>`<option value="${c.id}">${c.name}</option>`).join('')}
      </select>
      <div style="display:flex;gap:8px">
        <button id="btnQuote">Calcular costo</button>
        <button id="btnConfirm" disabled>Confirmar inscripción</button>
        <button id="btnStats" style="margin-left:auto">Ver estadísticas</button>
      </div>
      <div id="result" style="padding:8px;border:1px solid #ddd;border-radius:8px;display:none"></div>
    </div>
  `;

  const $ = (id:string)=>document.getElementById(id) as HTMLInputElement;
  const payload = () => ({
    name: $('name').value,
    cedula: $('cedula').value,
    email: $('email').value,
    schoolId: Number(($('schoolId') as unknown as HTMLSelectElement).value),
    careerId: Number(($('careerId') as unknown as HTMLSelectElement).value),
  });

  $('btnQuote').onclick = async ()=>{
    const res = await fetch(`${API}/inscripciones/cotizar`,{
      method:'POST', headers:{'Content-Type':'application/json'},
      body: JSON.stringify(payload())
    });
    const box = document.getElementById('result')!;
    if(!res.ok){ box.style.display='block'; box.innerHTML=`<span style="color:red">Error: ${await res.text()}</span>`; return; }
    state.quote = await res.json();
    box.style.display='block';
    if (state.quote) {
      box.innerHTML = `Base: $${state.quote.base.toFixed(2)} — Descuento: ${(state.quote.discountPct*100).toFixed(0)}% — Total: <b>$${state.quote.total.toFixed(2)}</b>`;
      $('btnConfirm').removeAttribute('disabled');
    } else {
      box.innerHTML = `<span style="color:red">Error: No se pudo calcular la cotización.</span>`;
    }
  };

  $('btnConfirm').onclick = async ()=>{
    const res = await fetch(`${API}/inscripciones/confirmar`,{
      method:'POST', headers:{'Content-Type':'application/json'},
      body: JSON.stringify(payload())
    });
    if(!res.ok){ alert('Error al registrar: '+await res.text()); return; }
    const {id} = await res.json();
    alert('Inscripción registrada. ID: '+id);
    loadData();
  };

  $('btnStats').onclick = ()=> renderStats();
}

async function renderStats(){
  const list = await fetch(`${API}/carreras/estadisticas`).then(r=>r.json());
  app.innerHTML = `
    <h2>Estado de inscripciones</h2>
    <ul>
      ${list.map((x:any)=>`<li><button data-id="${x.id}" class="link">${x.name}</button> — ${x.inscritos} inscritos</li>`).join('')}
    </ul>
    <div id="detail"></div>
    <button id="back">← Volver</button>
  `;
  document.querySelectorAll('.link').forEach(btn=>{
    btn.addEventListener('click', async ()=>{
      const id = (btn as HTMLButtonElement).dataset.id!;
      const res = await fetch(`${API}/carreras/${id}/inscritos`).then(r=>r.json());
      (document.getElementById('detail') as HTMLDivElement).innerHTML =
        `<h3>Detalle</h3>
         <table border="1" cellpadding="6">
           <tr><th>Nombre</th><th>Cédula</th><th>Email</th><th>Tipo colegio</th><th>Valor</th></tr>
           ${res.map((r:any)=>`<tr><td>${r.nombre}</td><td>${r.cedula}</td><td>${r.email}</td><td>${r.tipoColegio}</td><td>$${Number(r.valorPagado).toFixed(2)}</td></tr>`).join('')}
         </table>`;
    });
  });
  (document.getElementById('back') as HTMLButtonElement).onclick = ()=> renderForm();
}

loadData();
