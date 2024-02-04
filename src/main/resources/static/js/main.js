document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/gelir-gider-verileri')
        .then(response => response.json())
        .then(data => {
            const ctx = document.getElementById('gelirGiderGrafik').getContext('2d');
            const gelirGiderGrafik = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ['Gelir', 'Gider'],
                    datasets: [{
                        label: 'Gelir vs Gider',
                        data: [data.gelir, data.gider], // Sunucudan alınan veriler
                        backgroundColor: [
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 99, 132, 0.2)'
                        ],
                        borderColor: [
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 99, 132, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                }
            });
        });
});

document.getElementById('veriGetir').addEventListener('click', function() {
    const baslangicTarihi = document.getElementById('baslangicTarihi').value;
    const bitisTarihi = document.getElementById('bitisTarihi').value;


    const url = `/api/gelir-gider-verileri-tarih?baslangicTarihi=${baslangicTarihi}&bitisTarihi=${bitisTarihi}`;

    fetch(url)
        .then(response => response.json())
        .then(data => {

            const ctx = document.getElementById('gelirGiderGrafikTarih').getContext('2d');

            if (window.gelirGiderGrafikTarih) {
                window.gelirGiderGrafikTarih.destroy();
            }

            window.gelirGiderGrafikTarih = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ['Gelir', 'Gider'],
                    datasets: [{
                        data: [data.gelir, data.gider],
                        backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 99, 132, 0.2)'],
                        borderColor: ['rgba(75, 192, 192, 1)', 'rgba(255, 99, 132, 1)'],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                }
            });
        })
        .catch(error => console.error('Hata:', error));
});
