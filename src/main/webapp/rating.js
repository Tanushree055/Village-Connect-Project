document.addEventListener('DOMContentLoaded', () => {
    const stars = document.querySelectorAll('.star');
    const averageStars = document.querySelectorAll('.average-star');
    let selectedRating = 0;
    let userRated = false; // Flag to track if the user has rated

    // Handle star hover and click
    stars.forEach(star => {
        star.addEventListener('click', function() {
            if (userRated) {
                alert('You have already submitted your rating. You can only update your rating.');
                return;
            }

            selectedRating = this.getAttribute('data-value');
            updateStars();
        });

        star.addEventListener('mouseover', function() {
            const hoverRating = this.getAttribute('data-value');
            if (!userRated || hoverRating <= selectedRating) {
                updateStars(hoverRating);
            }
        });

        star.addEventListener('mouseout', function() {
            if (!userRated) {
                updateStars();
            }
        });
    });

    // Update the star colors based on rating
    function updateStars(rating = selectedRating) {
        stars.forEach(star => {
            if (parseInt(star.getAttribute('data-value')) <= rating) {
                star.classList.add('selected');
            } else {
                star.classList.remove('selected');
            }
        });
    }

    // Update average stars to reflect fractional ratings
    function updateAverageStars(averageRating) {
        averageStars.forEach((star, index) => {
            const starValue = index + 1;
            if (starValue < averageRating) {
                star.classList.add('active');
                star.classList.remove('partial');
                star.style.setProperty('--star-width', '100%');
            } else if (starValue === Math.floor(averageRating) + 1) {
                const partialWidth = (averageRating % 1) * 100;
                star.classList.add('partial');
                star.style.setProperty('--star-width', `${partialWidth}%`);
            } else {
                star.classList.remove('active', 'partial');
                star.style.setProperty('--star-width', '0');
            }
        });
    }

    // Handle form submission
    document.getElementById('submit').addEventListener('click', function() {
        if (selectedRating > 0) {
            fetch('http://localhost:8080/Village_Connect_Project/rating', {
                method: 'POST',
                headers: { 
                    'Content-Type': 'application/json' 
                },
                body: JSON.stringify({ rating: selectedRating })
            })
            .then(response => response.json())
            .then(data => {
                updateAverageStars(data.averageRating);
                userRated = true; // Mark that the user has rated
            })
            .catch(error => console.error('Error:', error));
        } else {
            alert('Please select a rating');
        }
    });
});
