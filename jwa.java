// ===========================
// 1. SIMPLE DATA (normally you’d fetch from an API)
// ===========================
const movies = [
    {
        title: "Inception",
        year: 2010,
        poster: "https://image.tmdb.org/t/p/w500/9gk7adHYeDvHkCSEqAvQNLV5Uge.jpg",
        genre: "Sci-Fi",
        trailer: "https://www.youtube.com/embed/YoHD9XEInc0"
    },
    {
        title: "The Dark Knight",
        year: 2008,
        poster: "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        genre: "Action",
        trailer: "https://www.youtube.com/embed/EXeTwQWrcwY"
    },
    {
        title: "Interstellar",
        year: 2014,
        poster: "https://image.tmdb.org/t/p/w500/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
        genre: "Sci-Fi",
        trailer: "https://www.youtube.com/embed/zSWdZVtXT7E"
    },
    {
        title: "Parasite",
        year: 2019,
        poster: "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
        genre: "Drama",
        trailer: "https://www.youtube.com/embed/5xH0HfJHsaY"
    },
    {
        title: "A Quiet Place",
        year: 2018,
        poster: "https://image.tmdb.org/t/p/w500/nAU74GmpUk7lP4t3X0Lc0XNHroR.jpg",
        genre: "Horror",
        trailer: "https://www.youtube.com/embed/WR7cc5t7tv8"
    },
    {
        title: "La La Land",
        year: 2016,
        poster: "https://image.tmdb.org/t/p/w500/uDO8zWDhfWwoFdKS4fzkUJt0iZ6.jpg",
        genre: "Romance",
        trailer: "https://www.youtube.com/embed/0pdqf4P9MB8"
    }
];

// ===========================
// 2. RENDER FUNCTIONS
// ===========================
const movieGrid = document.getElementById('movieGrid');
const genreGrid = document.getElementById('genreGrid');

function makeCard(movie) {
    return `
    <div class="movie-card" data-genre="${movie.genre}">
        <img src="${movie.poster}" alt="${movie.title}">
        <div class="card-info">
            <h3 class="card-title">${movie.title}</h3>
            <p class="card-year">${movie.year}</p>
            <button class="watch-btn" data-trailer="${movie.trailer}">Watch Trailer</button>
        </div>
    </div>`;
}

function renderMovies(list, target = movieGrid) {
    target.innerHTML = list.map(makeCard).join('');
}
renderMovies(movies); // initial load
renderMovies(movies, genreGrid); // genre section starts with all

// ===========================
// 3. GENRE FILTER
// ===========================
const genreTabs = document.getElementById('genreTabs');
genreTabs.addEventListener('click', e => {
    if (!e.target.classList.contains('genre-btn')) return;
    document.querySelector('.genre-btn.active').classList.remove('active');
    e.target.classList.add('active');
    const selected = e.target.dataset.genre;
    const filtered = selected === 'all' ? movies : movies.filter(m => m.genre === selected);
    renderMovies(filtered, genreGrid);
});

// ===========================
// 4. TRAILER MODAL
// ===========================
const modal = document.getElementById('trailerModal');
const trailerBox = document.getElementById('trailerBox');
const closeModal = document.querySelector('.close');

// delegate because cards are dynamically inserted
document.addEventListener('click', e => {
    if (e.target.classList.contains('watch-btn')) {
        const url = e.target.dataset.trailer;
        trailerBox.innerHTML = `<iframe width="100%" height="100%" src="${url}?autoplay=1" frameborder="0" allowfullscreen></iframe>`;
        modal.style.display = 'grid';
    }
});
closeModal.onclick = () => {
    modal.style.display = 'none';
    trailerBox.innerHTML = ''; // stop video
};
window.onclick = e => {
    if (e.target === modal) {
        modal.style.display = 'none';
        trailerBox.innerHTML = '';
    }
};

// ===========================
// 5. CONTACT FORM VALIDATION
// ===========================
const contactForm = document.getElementById('contactForm');
const formMsg = document.getElementById('formMsg');

contactForm.addEventListener('submit', e => {
    e.preventDefault();
    const name = contactForm.name.value.trim();
    const email = contactForm.email.value.trim();
    const msg = contactForm.message.value.trim();
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!name || !email || !msg) {
        showMsg('Please fill in all fields.', 'fail');
    } else if (!emailRegex.test(email)) {
        showMsg('Invalid email address.', 'fail');
    } else {
        showMsg('Thank you! Your message has been sent.', 'success');
        contactForm.reset();
    }
});

function showMsg(text, status) {
    formMsg.textContent = text;
    formMsg.className = status === 'fail' ? 'form-msg fail' : 'form-msg success';
    setTimeout(() => formMsg.textContent = '', 3000);
}

// ===========================
// 6. MOBILE NAV TOGGLE
// ===========================
const hamburger = document.getElementById('hamburger');
const mainNav = document.getElementById('navbar');
hamburger.onclick = () => mainNav.classList.toggle('open');

// ===========================
// 7. DYNAMIC YEAR IN FOOTER
// ===========================
document.getElementById('year').textContent = new Date().getFullYear();