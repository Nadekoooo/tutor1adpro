Refleksi Exercise 1:

Pada implementasi fitur edit dan delete produk, saya telah menerapkan prinsip pemisahan tanggung jawab dengan memisahkan kode antara Controller, Service, dan Repository. 
Penggunaan dependency injection melalui anotasi @Autowired meningkatkan modularitas serta memudahkan proses pengujian kode. 
Penamaan kelas, method, dan variabel dilakukan secara deskriptif dan konsisten, sehingga memudahkan pemahaman dan pemeliharaan kode.

View resolver yang ada dari Thymeleaf juga mampu berguna untuk memetakan nama view pada controller ke template yang sesuai.
Seluruh input produk yang ada sudah di bind melalui @ModelAttribute dan beberapa tahap validasi meningkatkan integrity data yang ada.
Overall, cleancode dan secure coding sudah saya coba terapkan di kode ini, dan hal yang tersisa ialah validasi input untuk keamanan lebih lanjut.