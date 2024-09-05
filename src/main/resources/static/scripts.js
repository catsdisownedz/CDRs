document.addEventListener('DOMContentLoaded', function () {
    // Fetch CDRs and Users when the page loads
    fetchCDRs();
    fetchUsers();
});

async function fetchCDRs() {
    try {
        const response = await fetch('/api/cdrs');
        const data = await response.json();
        const cdrsTableBody = document.getElementById('cdrs-table-body');
        cdrsTableBody.innerHTML = data.map(cdr => `
            <tr>
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
        const data = await response.json();
        const usersTableBody = document.getElementById('users-table-body');
        usersTableBody.innerHTML = data.map(user => `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error fetching users:', error);
    }
}

async function fetchUser(username) {
    try {
        const response = await fetch(`/api/users/${username}`);
        const data = await response.json();
        document.getElementById('user-details').innerHTML = JSON.stringify(data, null, 2);
    } catch (error) {
        console.error('Error fetching user:', error);
    }
}

async function createCDR() {
    const cdr = {
        startDateTime: new Date().toISOString(),
        endDateTime: new Date().toISOString(),
        usage: 0,
        serviceType: "call",
        anum: "123456",
        bnum: "654321"
    };
    try {
        const response = await fetch('/api/cdrs', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(cdr),
        });
        const data = await response.json();
        console.log('Created CDR:', data);
        fetchCDRs(); // Refresh the list
    } catch (error) {
        console.error('Error creating CDR:', error);
    }
}

async function createUser() {
    const user = {
        username: document.getElementById('new-username').value,
        password: document.getElementById('new-password').value
    };
    try {
        const response = await fetch('/api/users/', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user),
        });
        const data = await response.json();
        console.log('Created User:', data);
    } catch (error) {
        console.error('Error creating user:', error);
    }
}
