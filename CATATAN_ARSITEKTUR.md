Nama: Andi Muh Rafi Khairan NIM: 52024002 
Pertemuan: 1 - Arsitektur & Lanskap Teknologi Mobile Modern

1. Mengapa CampusConnect dibangun secara native (Kotlin + Jetpack Compose), bukan Flutter/hybrid?

   CampusConnect saat ini hanya menargetkan Android, jadi pendekatan native paling sesuai. Aplikasi native berjalan langsung di atas Android Runtime sehingga performanya maksimal dan akses ke fitur perangkat penuh. Hybrid (WebView) ditolak karena UI dirender browser sehingga performa dan pengalaman penggunanya paling rendah. Flutter tidak dipilih karena memakai Dart dan tidak memberi keuntungan berarti untuk satu platform, sedangkan kita sudah memakai Kotlin. Compose yang deklaratif juga memudahkan pengembangan, dan Kotlin menjadi jalan menuju KMP pada Pertemuan 2
2. Konsekuensi jika dirilis di iOS

   Pada pendekatan native, reuse kode antar-platform sekitar 0%, sehingga UI dan logika harus ditulis ulang dengan Swift/SwiftUI. Akibatnya ada dua basis kode yang dipelihara paralel, setiap perubahan dikerjakan dua kali, dan biaya serta waktu pengembangan hampir berlipat. Mitigasinya dengan memindahkan logika bisnis ke Kotlin Multiplatform (commonMain) agar sebagian kode bisa dibagikan, sementara UI tetap native atau memakai Compose Multiplatform.