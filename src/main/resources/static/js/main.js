/**
 * main.js
 */
$(document).ready(function () {
    // main page scroll 효과
    window.addEventListener('scroll', function() {
        const reveals = document.querySelectorAll('.reveal');
        reveals.forEach(function(reveal) {
            const windowHeight = window.innerHeight;
            const elementTop = reveal.getBoundingClientRect().top;
            const revealPoint = 150;
    
            if (elementTop < windowHeight - revealPoint) {
                reveal.classList.add('active');
            }
        });
    });    
});