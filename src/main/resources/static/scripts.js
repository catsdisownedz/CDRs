document.addEventListener('DOMContentLoaded', function () {
    fetchData();
});

async function fetchData() {
    try {
        await fetchCDRs();
        await fetchUsers();
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

async function fetchCDRs() {
    try {
        const response = await fetch('/api/cdrs');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        const cdrsTableBody = document.getElementById('cdrs-table-body');
        cdrsTableBody.innerHTML = data.map(cdr => `
            <tr style="background-color: ${getRandomPastelColor()}">
                <td>${cdr.id}</td>
                <td>${cdr.anum}</td>
                <td>${cdr.bnum}</td>
                <td>${cdr.serviceType}</td>
                <td>${cdr.usage}</td>
                <td>${cdr.startDateTime}</td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error fetching CDRs:', error);
    }
}
async function fetchUsers() {
    try {
        const response = await fetch('/api/users');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json();
        const usersTableBody = document.getElementById('users-table-body');
        usersTableBody.innerHTML = data.map(user => `
            <tr style="background-color: ${getRandomPastelColor()}">
                <td>${user.id}</td>
                <td>${user.username}</td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error fetching users:', error);
    }
}


function getRandomPastelColor() {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    return `rgba(${r}, ${g}, ${b}, 0.3)`;
}
