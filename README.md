# TP6DPBO2025C1

# Janji
Saya Shizuka Maulia Putri dengan NIM 2308744mengerjakan soal TP 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya, maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Alur Program
1. StartMenu
StartMenu adalah sebuah JFrame (window utama) yang menampilkan: Background gambar (bg_awal_3.jpg) dan Tombol "PRESS TO START"
Saat user klik tombol "PRESS TO START":
- Jendela StartMenu ditutup (dispose())
- Jendela baru untuk game Flappy Bird dibuat dan ditampilkan (FlappyBird panel dimasukkan ke JFrame baru)


2. FlappyBird
Kelas FlappyBird adalah JPanel tempat semua elemen game digambar dan diatur.
Yang dilakukan dalam constructor:
Mengatur ukuran panel dan menambahkan listener keyboard
Memuat gambar: background, burung, dan pipa
Membuat objek player (burung)
Inisialisasi array pipes untuk menyimpan pipa
Memulai timer: gameLoop (60 FPS): untuk memanggil actionPerformed() → update pergerakan. pipesCooldown (setiap 5 detik): menambahkan pipa baru ke layar
3. Game Loop (Timer gameLoop)
Dipanggil setiap 1/60 detik:
1. move() dijalankan:
- Menambahkan gravitasi ke burung (jatuh ke bawah)
- Cek apakah burung menyentuh tanah → Game Over
- Untuk setiap pipa: Gerakkan pipa ke kiri, Cek tabrakan dengan burung → Game Over, Cek apakah burung sukses melewati pipa atas → Tambah skor


2. repaint() → memanggil paintComponent() untuk menggambar ulang panel


4. Kontrol Keyboard
Jika user menekan Spasi: burung akan "meloncat" ke atas (velocityY = -10)
Jika Game Over, dan user tekan R: Game di-reset dengan memanggil restartGame()




