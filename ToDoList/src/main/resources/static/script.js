const API_BASE = "http://localhost:8082/todolist";

document.addEventListener("DOMContentLoaded", loadItems);

// 🔹 GET ALL
function loadItems() {
    fetch(`${API_BASE}/getAllitems`)
        .then(res => res.json())
        .then(items => {
            const list = document.getElementById("todoList");
            list.innerHTML = "";

            items.forEach(item => {
                const li = document.createElement("li");
                li.className = item.completed ? "completed" : "";

                li.innerHTML = `
                    <b>${item.title}</b><br>
                    <small>${item.description || ""}</small><br>

                    <button onclick="toggleCompleted(${item.itemId}, ${item.completed})">
                        ${item.completed ? "Undo" : "Done"}
                    </button>

                    <button onclick="editItem(${item.itemId}, '${item.title}', '${item.description || ""}')">
                        ✏️ Edit
                    </button>

                    <button onclick="deleteItem(${item.itemId})">❌ Delete</button>
                `;

                list.appendChild(li);
            });
        });
}

// ➕ ADD
function addItem() {
    const title = document.getElementById("titleInput").value.trim();
    const description = document.getElementById("descInput").value.trim();

    if (!title) {
        alert("❌ Title is required");
        return;
    }

    fetch(`${API_BASE}/createItem`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            title,
            description,
            completed: false
        })
    })
    .then(res => {
        if (!res.ok) throw new Error();
        return res.json();
    })
    .then(() => {
        alert("✅ Are you sure to added the Item successfully!");
        document.getElementById("titleInput").value = "";
        document.getElementById("descInput").value = "";
        loadItems();
    })
    .catch(() => alert("❌ Failed to add item"));
}

// ✏️ EDIT (title + description)
function editItem(id, oldTitle, oldDesc) {
    const newTitle = prompt("Edit title", oldTitle);
    if (newTitle === null || newTitle.trim() === "") return;

    const newDesc = prompt("Edit description", oldDesc);

    fetch(`${API_BASE}/updateItembyId/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            title: newTitle,
            description: newDesc !== null ? newDesc : oldDesc
        })
    })
    .then(res => res.json())
    .then(() => {
        alert("✏️ Item updated successfully!");
        loadItems();
    });
}

// ✅ TOGGLE COMPLETED
function toggleCompleted(id, completed) {
    fetch(`${API_BASE}/updateItembyId/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            completed: !completed
        })
    })
    .then(res => res.json())
    .then(() => {
        alert(completed ? "↩️ Item marked as pending" : "✅ Item marked as done");
        loadItems();
    });
}

// ❌ DELETE
function deleteItem(id) {
    if (!confirm("Are you sure you want to delete this item?")) return;

    fetch(`${API_BASE}/deleteItembyId/${id}`, {
        method: "DELETE"
    })
    .then(() => {
        alert("🗑️ Item deleted successfully!");
        loadItems();
    });
}
