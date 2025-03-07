# Refleksi Exercise 1.1

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



# Reflection 1.2: Unit Testing & Functional Test Code Cleanliness

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

# Refleksi 2.1: CI/CD, Analisis Kode, dan Deploy Otomatis

## 1. Perbaikan Isu Kualitas Kode

- **Dependency Action Pinning:**
  - *Masalah:* Awalnya workflow menggunakan tag versi (misalnya `@v4`) tanpa dipin ke commit hash tertentu, yang dapat menyebabkan ketidakpastian dan potensi masalah keamanan.
  - *Solusi:* Mengupdate file workflow untuk menggunakan commit hash spesifik pada action seperti `actions/checkout` dan `actions/setup-java`, sehingga memastikan dependensi yang konsisten dan aman.

- **Pengaturan Token Permissions:**
  - *Masalah:* Workflow CI awal tidak memiliki blok `permissions` di tingkat atas, sehingga GITHUB_TOKEN memiliki akses yang terlalu luas.
  - *Solusi:* Menambahkan blok `permissions: { contents: read }` untuk membatasi akses token hanya untuk keperluan membaca konten repositori.

- **Integrasi Alat Analisis Statis:**
  - *Masalah:* Tidak adanya integrasi alat analisis statis yang otomatis untuk mendeteksi potensi masalah kualitas dan keamanan kode.
  - *Solusi:* Mengintegrasikan PMD dan CodeQL dalam workflow untuk menjalankan analisis secara otomatis dan menghasilkan laporan SARIF.

- **Coverage Test yang Kurang Lengkap:**
  - *Masalah:* Beberapa cabang logika, terutama skenario negatif (misalnya pencarian produk yang tidak ada), tidak tercakup dalam test suite.
  - *Solusi:* Menambahkan test case tambahan untuk memastikan bahwa setiap alur, baik positif maupun negatif, telah diuji secara menyeluruh.

- **CII Best Practice:**
  - *Masalah:* Di sini memerlukan badge best practice di web, hal ini terdapat pada website https://www.bestpractices.dev, di sini memerlukan beberapa penyesuaian kode.
  - *Solusi:* Saya menyelesaikan semua basic testcase, dan badge sudah berhasil didapatkan di sini.

## 2. Evaluasi Implementasi CI/CD

Implementasi CI/CD saat ini telah memenuhi definisi Continuous Integration dan Continuous Deployment karena setiap perubahan pada kode secara otomatis memicu proses build, pengujian, dan analisis kualitas kode. 
Pada repo ini setiap commit diuji secara otomatis, sehingga potensi bug atau kesalahan dapat segera dideteksi dan diperbaiki sebelum integrasi ke branch utama. 
Pipeline yang ada memberikan umpan balik cepat kepada tim pengembang sehingga proses perbaikan dan refactoring dapat dilakukan secara efisien. 
Selain itu, deployment otomatis ke lingkungan produksi melalui PaaS memastikan bahwa kode terbaru langsung dirilis tanpa intervensi manual. Dengan proses ini, kualitas aplikasi meningkat dan risiko kesalahan produksi dapat diminimalkan.

## 3. Kesimpulan

Implementasi CI/CD ini telah meningkatkan kualitas dan keandalan pengembangan aplikasi. Dengan otomatisasi pengujian, analisis kualitas, dan deployment, proses rilis menjadi lebih cepat, aman, dan konsisten. Integrasi dengan alat analisis statis dan penambahan test case untuk berbagai skenario (positif, negatif, dan edge case) membantu memastikan bahwa setiap perubahan diuji dengan baik sebelum di-deploy ke lingkungan produksi.


# Refleksi 3: OO Principles & Software Maintainability

## 1) Prinsip SOLID yang Diterapkan dalam Proyek

Dalam proyek ini, saya menerapkan beberapa prinsip SOLID, di antaranya adalah Single Responsibility Principle (SRP), Open/Closed Principle (OCP), dan Dependency Inversion Principle (DIP). SRP memastikan bahwa setiap kelas memiliki satu tanggung jawab yang jelas, misalnya kelas `CarRepository` hanya bertugas mengelola data mobil tanpa mencampur logika lain seperti validasi atau pembuatan objek. Dengan OCP, kode dapat diperluas tanpa mengubah kode yang sudah ada, contohnya ketika menambahkan fitur baru pada repository tanpa mengubah logika dasar operasi CRUD. DIP diterapkan agar modul tingkat tinggi tidak bergantung pada modul tingkat rendah secara langsung, sehingga komponen-komponen dalam proyek bisa saling menggantikan dan diuji secara independen. Selain itu, prinsip Interface Segregation Principle (ISP) juga dipertimbangkan ketika mendesain antarmuka, sehingga pengguna kelas tidak dipaksa bergantung pada metode yang tidak mereka butuhkan. Prinsip SOLID ini membantu dalam menciptakan arsitektur yang modular dan fleksibel, memudahkan pemeliharaan dan pengembangan lebih lanjut. Dengan menerapkan prinsip-prinsip tersebut, proyek menjadi lebih scalable dan mudah diadaptasi sesuai perubahan kebutuhan.

## 2) Keuntungan Menerapkan Prinsip SOLID pada Proyek

Menerapkan prinsip SOLID memberikan banyak keuntungan yang signifikan bagi pengembangan proyek. Pertama, kode menjadi lebih mudah dipahami karena setiap kelas memiliki tanggung jawab tunggal, yang mengurangi kompleksitas dan memudahkan debugging. Contohnya, dengan SRP, perubahan pada logika repository tidak akan berdampak pada kelas lain yang tidak terkait. Kedua, penerapan OCP memungkinkan pengembang untuk menambahkan fitur baru tanpa merusak fungsionalitas yang sudah ada, sehingga meningkatkan kestabilan sistem. Selain itu, DIP dan ISP membuat komponen-komponen proyek lebih loosely coupled, sehingga perubahan pada satu komponen tidak menimbulkan efek domino pada komponen lainnya. Hal ini memudahkan pengujian unit secara terpisah dan meningkatkan keandalan sistem secara keseluruhan. Penggunaan SOLID juga meningkatkan fleksibilitas dalam hal pemeliharaan, karena struktur kode yang modular memungkinkan tim untuk mengerjakan bagian tertentu secara independen. Secara keseluruhan, penerapan SOLID membuat proyek lebih mudah dikembangkan, dipelihara, dan disesuaikan dengan kebutuhan bisnis yang berubah.

## 3) Kerugian Tidak Menerapkan Prinsip SOLID pada Proyek

Tidak menerapkan prinsip SOLID dapat menyebabkan beberapa masalah serius dalam pengembangan perangkat lunak. Pertama, jika sebuah kelas memiliki lebih dari satu tanggung jawab (melanggar SRP), maka perubahan pada satu fungsi dapat mempengaruhi fungsi lainnya, sehingga meningkatkan risiko bug. Misalnya, jika logika validasi dicampur dengan logika penyimpanan data dalam `CarRepository`, maka kesalahan dalam validasi dapat memengaruhi operasi CRUD secara keseluruhan. Kedua, tanpa OCP, setiap kali ada penambahan fitur baru, kode yang sudah ada perlu dimodifikasi secara langsung, yang meningkatkan risiko regresi dan menurunkan stabilitas sistem. Ketiga, tanpa DIP, komponen-komponen dalam sistem akan sangat bergantung satu sama lain, sehingga membuat refactoring atau penggantian modul menjadi sulit dan berisiko. Selain itu, pengujian unit menjadi lebih kompleks karena keterkaitan yang erat antara kelas, sehingga meningkatkan waktu dan biaya pengujian. Akhirnya, struktur kode yang tidak modular menyebabkan sulitnya pemeliharaan dan skalabilitas, sehingga proyek sulit untuk berkembang seiring dengan perubahan kebutuhan bisnis. Secara keseluruhan, tidak menerapkan prinsip SOLID dapat mengakibatkan sistem yang rapuh, sulit dipelihara, dan rentan terhadap bug.


# Refleksi 4: Test-Driven Development & Refactoring


## Evaluasi TDD Workflow Berdasarkan Percival (2017)

Menurut Percival (2017) dalam "Principles and Best Practice of Testing", alur TDD memiliki beberapa keuntungan yang signifikan, antara lain:

- **Feedback Cepat:** Dengan menulis test terlebih dahulu, saya mendapatkan umpan balik yang cepat mengenai apakah kode yang ditulis sudah memenuhi spesifikasi. Hal ini membantu mendeteksi kesalahan sejak dini.
- **Desain Kode yang Lebih Baik:** TDD mendorong saya untuk membuat desain kode yang modular dan terpisah dengan baik, sehingga memudahkan proses pemeliharaan dan pengembangan di masa mendatang.
- **Kepercayaan pada Kode:** Dengan adanya test yang telah lulus, saya merasa lebih yakin bahwa perubahan pada kode tidak akan merusak fungsionalitas yang sudah ada.

Namun, ada beberapa hal yang perlu diperbaiki untuk iterasi TDD selanjutnya:
- **Coverage Test yang Lebih Lengkap:** Saya harus memastikan semua skenario, terutama edge case, sudah tercakup dalam test.
- **Refactoring Test Secara Berkala:** Seiring dengan perubahan kode, test harus direview dan direfaktor agar tetap relevan dan mudah dipahami.
- **Dokumentasi Test yang Lebih Jelas:** Penulisan dokumentasi pada setiap test perlu diperbaiki agar anggota tim lain dapat dengan mudah memahami maksud dari masing-masing test.

## Evaluasi Penerapan Prinsip F.I.R.S.T

Prinsip F.I.R.S.T (Fast, Independent, Repeatable, Self-Validating, Timely) merupakan panduan yang baik untuk menulis unit test. Berdasarkan implementasi saat ini:

- **Fast:** Test berjalan dengan cepat karena hanya menguji unit-unit kecil dengan bantuan mocking untuk komponen eksternal.
- **Independent:** Setiap test sudah berjalan secara independen tanpa saling bergantung, sehingga hasil test tidak dipengaruhi oleh urutan eksekusi test.
- **Repeatable:** Test dapat dijalankan berulang kali dengan hasil yang konsisten selama lingkungan testing tidak berubah.
- **Self-Validating:** Setiap test memiliki asersi yang jelas sehingga hasilnya langsung dapat dinilai (pass/fail) tanpa perlu interpretasi manual.
- **Timely:** Test ditulis sesuai dengan prinsip TDD, yaitu sebelum implementasi kode, sehingga membantu memandu proses pengembangan.

Meskipun penerapan prinsip F.I.R.S.T sudah cukup baik, ada beberapa area untuk peningkatan:
- **Keterbacaan dan Penamaan Test:** Saya perlu memperbaiki nama-nama test dan struktur kode test agar lebih mudah dipahami oleh semua anggota tim.
- **Pesan Error yang Lebih Informatif:** Setiap asersi sebaiknya dilengkapi dengan pesan error yang jelas agar proses debugging bisa dilakukan dengan lebih efisien.
- **Cakupan Test untuk Edge Case:** Menambahkan skenario test untuk kondisi-kondisi yang jarang terjadi namun berpotensi menimbulkan bug.

## Kesimpulan

Secara keseluruhan, alur TDD yang saya terapkan sangat berguna untuk memastikan kualitas dan keandalan kode. Namun, ke depannya saya akan:
- Menulis test dengan penamaan yang lebih deskriptif dan terstruktur.
- Memperluas cakupan test untuk mencakup lebih banyak edge case.
- Memperbaiki dokumentasi dan pesan error pada test untuk memudahkan proses debugging.

