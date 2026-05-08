document.addEventListener('DOMContentLoaded', () => {
    // Theme Toggle
    const themeToggleBtn = document.getElementById('themeToggle');
    
    // Check for saved theme preference or system preference
    if (localStorage.theme === 'dark' || (!('theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
        document.documentElement.classList.add('dark');
    } else {
        document.documentElement.classList.remove('dark');
    }

    themeToggleBtn.addEventListener('click', () => {
        document.documentElement.classList.toggle('dark');
        if (document.documentElement.classList.contains('dark')) {
            localStorage.theme = 'dark';
        } else {
            localStorage.theme = 'light';
        }
    });

    // Auth State
    const authSection = document.getElementById('authSection');
    const plannerSection = document.getElementById('plannerSection');
    const logoutBtn = document.getElementById('logoutBtn');
    
    const checkAuth = () => {
        const token = localStorage.getItem('jwt_token');
        if (token) {
            authSection.classList.add('hidden');
            plannerSection.classList.remove('hidden');
            logoutBtn.classList.remove('hidden');
        } else {
            authSection.classList.remove('hidden');
            plannerSection.classList.add('hidden');
            logoutBtn.classList.add('hidden');
        }
    };
    checkAuth();

    // Login Form
    const loginForm = document.getElementById('loginForm');
    const loginError = document.getElementById('loginError');

    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        try {
            const response = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                const data = await response.json();
                localStorage.setItem('jwt_token', data.token);
                loginError.classList.add('hidden');
                checkAuth();
            } else {
                loginError.textContent = 'Invalid credentials';
                loginError.classList.remove('hidden');
            }
        } catch (error) {
            loginError.textContent = 'Server error. Please try again.';
            loginError.classList.remove('hidden');
        }
    });

    // Logout
    logoutBtn.addEventListener('click', () => {
        localStorage.removeItem('jwt_token');
        checkAuth();
    });

    // Plan Form
    const planForm = document.getElementById('planForm');
    const generateBtn = document.getElementById('generateBtn');
    const loadingSpinner = document.getElementById('loadingSpinner');
    const resultContainer = document.getElementById('resultContainer');
    const emptyState = document.getElementById('emptyState');
    const planError = document.getElementById('planError');

    planForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        // Gather data
        const destination = document.getElementById('destination').value;
        const durationDays = parseInt(document.getElementById('durationDays').value);
        const travelers = parseInt(document.getElementById('travelers').value);
        const budget = document.getElementById('budget').value;
        
        const interests = Array.from(document.querySelectorAll('input[name="interest"]:checked'))
                              .map(cb => cb.value);

        const token = localStorage.getItem('jwt_token');
        if (!token) return checkAuth();

        // UI updates
        generateBtn.disabled = true;
        loadingSpinner.classList.remove('hidden');
        planError.classList.add('hidden');
        emptyState.classList.add('hidden');
        resultContainer.classList.remove('hidden');
        resultContainer.innerHTML = '<div class="flex justify-center py-20"><div class="animate-pulse flex flex-col items-center"><div class="h-4 w-48 bg-gray-200 dark:bg-gray-700 rounded mb-4"></div><div class="h-4 w-64 bg-gray-200 dark:bg-gray-700 rounded mb-4"></div><div class="h-4 w-52 bg-gray-200 dark:bg-gray-700 rounded"></div></div></div>';

        try {
            const response = await fetch('/api/travel/plan', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify({
                    destination, durationDays, budget, interests, travelers
                })
            });

            if (response.ok) {
                const data = await response.json();
                resultContainer.innerHTML = data.itinerary;
                resultContainer.classList.add('animate-fade-in-up');
            } else if (response.status === 401 || response.status === 403) {
                localStorage.removeItem('jwt_token');
                checkAuth();
            } else {
                throw new Error('Failed to generate plan');
            }
        } catch (error) {
            planError.textContent = error.message;
            planError.classList.remove('hidden');
            resultContainer.classList.add('hidden');
            emptyState.classList.remove('hidden');
        } finally {
            generateBtn.disabled = false;
            loadingSpinner.classList.add('hidden');
        }
    });
});
