# Refleksi Exercise 1

## 1. Pendahuluan
Pada implementasi fitur edit dan delete produk, saya berupaya menerapkan prinsip-prinsip clean code dan secure coding yang telah dipelajari. Fokus utama adalah pada pemisahan tanggung jawab (separation of concerns), penggunaan dependency injection, penamaan yang konsisten, pemanfaatan Thymeleaf sebagai view resolver, serta binding dan validasi input.

## 2. Pemisahan Tanggung Jawab (Separation of Concerns)
- **Controller:**  
  Menangani request dan response dari pengguna serta meneruskan data ke layer Service.
- **Service:**  
  Mengandung logika bisnis yang memproses data sebelum disimpan atau diubah.
- **Repository:**  
  Bertanggung jawab untuk mengelola akses data (dalam hal ini menggunakan struktur data in-memory).

## 3. Dependency Injection
- **Penggunaan @Autowired:**  
  Memungkinkan injeksi dependensi antar komponen sehingga masing-masing komponen menjadi lebih modular dan mudah diuji.
- **Keuntungan:**  
  Memudahkan proses testing dengan memungkinkan penggunaan mock objects untuk tiap komponen.

## 4. Penamaan dan Konsistensi Kode
- **Penamaan yang Deskriptif:**  
  Kelas, method, dan variabel dinamai secara jelas sesuai fungsinya (misalnya, `ProductController`, `ProductService`, dan `ProductRepository`).
- **Konsistensi:**  
  Struktur dan naming convention yang konsisten memudahkan pemahaman dan pemeliharaan kode.

## 5. Pemanfaatan Thymeleaf sebagai View Resolver
- **Mapping View:**  
  Thymeleaf secara otomatis memetakan nama view yang dikembalikan oleh controller ke template yang sesuai (misalnya, `EditProduct.html` dan `CreateProduct.html`).
- **Struktur Tampilan:**  
  Membantu menjaga tampilan front-end tetap terstruktur dan mudah dimodifikasi.

## 6. Binding dan Validasi Input
- **Binding dengan @ModelAttribute:**  
  Seluruh input produk di-bind ke objek model sehingga meminimalkan kesalahan dalam pengambilan data.
- **Validasi Input:**  
  Meskipun validasi dasar sudah diterapkan, masih terdapat ruang untuk peningkatan dengan menambahkan validasi lebih mendalam (misalnya, menggunakan `@Valid` dan `BindingResult`) guna memastikan integritas data dan meningkatkan keamanan aplikasi.

## 7. Kesimpulan dan Rekomendasi
- **Penerapan Clean Code & Secure Coding:**  
  Secara keseluruhan, prinsip clean code dan secure coding telah diterapkan dengan baik melalui pemisahan tanggung jawab, penggunaan dependency injection, dan konsistensi penamaan.
- **Peningkatan Validasi:**  
  Langkah selanjutnya adalah meningkatkan validasi input untuk menambah lapisan keamanan, sehingga aplikasi menjadi lebih robust dan siap untuk skala produksi.



# Reflection 2: Unit Testing & Functional Test Code Cleanliness

1. ## Setelah Menulis Unit Test
Setelah menulis unit test, saya merasa lebih percaya diri dengan kualitas kode yang telah dibuat. Unit test membantu mendeteksi bug sejak dini dan memberikan dasar yang kuat untuk refactoring serta pengembangan fitur baru.

## Jumlah Unit Test dalam Satu Kelas
Tidak ada jumlah baku unit test per kelas. Unit test sebaiknya mencakup semua kemungkinan skenario, termasuk skenario positif, negatif, dan edge case. Fokus utamanya adalah memastikan setiap alur logika dan fungsi aplikasi telah diuji secara menyeluruh.

## Memastikan Unit Test Sudah Cukup
Untuk memastikan unit test telah mencakup seluruh kode, kita dapat menggunakan metrik **code coverage**. Meskipun mencapai 100% code coverage merupakan indikator bahwa setiap baris kode telah dieksekusi selama testing, hal ini tidak berarti kode bebas dari bug atau error. Code coverage tidak selalu mencerminkan kualitas test case atau skenario yang kompleks.

2. ## Refleksi Mengenai Pembuatan Functional Test Suite Baru
Setelah menulis *CreateProductFunctionalTest.java*, jika diminta membuat functional test suite baru untuk memverifikasi jumlah item pada product list dengan setup dan variabel instance yang sama, ada beberapa hal yang menurut saya perlu kita note:
- **Duplikasi Kode:** Pengulangan setup prosedur dan variabel instance di banyak kelas test dapat menyebabkan duplikasi, yang menyulitkan pemeliharaan kode di masa depan.
- **Modularitas dan Reusabilitas:** Idealnya, kode test yang serupa sebaiknya diekstraksi ke dalam kelas dasar (base test class) atau metode utilitas, sehingga setup yang sama hanya didefinisikan sekali dan dapat digunakan ulang oleh seluruh test suite.
- **Kebersihan Kode Test:** Duplikasi dan kode yang tidak terstrukt|ur dapat menurunkan kualitas keseluruhan kode test. Hal ini juga berpotensi menyulitkan debugging dan memperlambat pengembangan fitur baru.
- **Perbaikan yang mungkin lebih optimal seperti berikut:**
  - Memakai kelas dasar untuk setup bersama yang mencakup instance variabel dan prosedur inisialisasi yang umum.
  - Menerapkan prinsip DRY (Don't Repeat Yourself) agar setiap perubahan hanya perlu dilakukan di satu tempat.
  - Pertimbangkan untuk menggunakan parameterized test jika ada test yang hampir identik namun dengan data berbeda.

## Kesimpulan
Meskipun unit test dan functional test membantu memastikan stabilitas aplikasi dan mencapai code coverage tinggi adalah hal yang baik, 100% code coverage tidak menjamin bebas bug. Kita perlu untuk mereview kode, menghindari dupe, dan memahami kode sehingga testing berjalan dengan baik dan optimal.

