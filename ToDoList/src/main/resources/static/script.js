const API_BASE = "http://localhost:8082/todolist";

/**
 * ➕ ADD ITEM
 * - ItemNumber popup
 * - Auto copy
 * - Redirect to items.html
 */
function addItem() {
    const title = document.getElementById("titleInput").value.trim();
    const description = document.getElementById("descInput").value.trim();

    if (!title) {
        alert("❌ Title is required");
        return;
    }

    fetch(`${API_BASE}/createItem`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            title: title,
            description: description,
            completed: false
        })
    })
    .then(res => {
        if (!res.ok) {
            throw new Error("Create failed");
        }
        return res.json();
    })
    .then(item => {
        // ✅ Popup with ItemNumber
        alert(
            `✅ Item Added Successfully!\n\n` +
            `Item Number: ${item.itemNumber}\n\n` +
            `Item Number copied to clipboard`
        );

        // 📋 Copy ItemNumber
        navigator.clipboard.writeText(item.itemNumber);

        // 🔁 Clear inputs
        document.getElementById("titleInput").value = "";
        document.getElementById("descInput").value = "";

        // 🔥 Redirect to second page (horizontal table)
        window.location.href = "items.html";
    })
    .catch(err => {
        console.error(err);
        alert("❌ Failed to add item");
    });
}
