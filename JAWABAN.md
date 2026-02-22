## LATIHAN 1
**EKSPERIMEN 1: @Controller vs @RestController**
Buka /test/view  → Apa yang muncul?   muncul "Ini dari @Controller"
Buka /test/text  → Apa yang muncul?   muncul "Ini dari @Controller + @RespronseBody -> text langsung"
Apa perbedaannya?  ->Perbedaannya adalah /test/view menggunakan @Controller tanpa @responseBody, sehingga nilai yang dikembalikan dianggap sebagi nama file template yang ada difolder templates, Sedangkan /test/text menampilakan teks langsung di browser karena menggunakan @ResponseBody, sehinngga tidka merenser template.
Kesimpulan:
@Controller tanpa @ResponseBody -> retur nama tempalte
@ResponseBody -> retur data langsung

**EKSPERIMEN 2**
Apakah berhasil? Tidak
HTTP Status code: 500
Error message: Internal Server Error
(Template not foud / Error resolving template)
kesimpulan:
Jika COntroller return nama view yang tidak ada, Sring akan mengembalikan error 500 (Internal Server Error) karena template/view yang diminta tidak ditemukan sehingga gagal dirender oleh view.

**EKSPERIMEN 3**
1. /greet
   ->Selamat Pagi, Mahasiswa!
2. /greet?name=Budi
   ->Selamat Pagi, Budi!
3. /greet?name=Budi&waktu=Siang
   ->Selamat Sinag, Budi!
4. /greet/Ani
   ->Halo, Ani!
5. /greet/Ani/detail
   ->Halo, Ani!
6. /greet/Ani/detail?lang=En
   ->Hello, Ani!

**EKSPERIMEN 4**
1. Apa perbedaan antara @Controller dan @RestController? Dalam kasus apa kamu pakai masing-masing?
- @Controller: return nama template (HTML)
- @RestController: return data langsung (JSON/text)
2. Perhatikan bahwa template product/list.htnl dipakai oleh 3 endpoint beerbeda (list all, filter by category,search).Apa keuntungannya membuat template yang reusable seperti ini?
- Tidak duplikat file
- Mudah maintenace
- Lebih rapi
3. Kenapa Controller inject ProductService (bukan langsung akses data di ArrayList)? Apa yang terjadi kalau Controller langsung manage data?
- Biar tugas terpisah
- Lebih rapi dan scalable
4. Apa perbedaan model.addAttribute("products", products) dengan return products langsung seperti di @RestController?
- addAtribute: kirim ke HTML
- return langsung: kirim ke JSON/date
5. Jika kamu buka http://localhost:8080/products/abc (ID bukan angka), apa yang terjadi? Kenapa?
- Error 400 karena "abc" tidak bisa diubah ke long
6. Apa keuntungan pakai @RequestMapping("/products") di level class dibanding menulis full path di setiap @GetMapping?
- Tidak perlu tulis / products berulang
- Lebih bersih
7. Dalam lab ini, kata "Model" muncul dalam beberapa konteks berbeda. Sebutkan minimal 2 arti yang berbeda dan jelaskan perbedaannya.
- Model parameter: kirim data ke view
- Model/product: class date

## LATIHAN 2
** Ekperimen 1: Fragment Tidak Ada
- Apakah error? :    [ya]
- Error message:     [type=Internal Server Error, status=500].
  Jika nama frament salah, Thymeleaf akan menampilakna error (TamplateInputException) dan halaman tidak bisa dirender.
  ** Eksperimen 2: Satatic Resource
- CSS masih bekerja:  [ya]
- Apakah halaman error?:  [Tidak]
- Apakah CSSditerapkan?:  [Tidak]
  KESIMPULAN : Kesimpulan: th:href="@{}" lebih baik karena Thymeleaf akan membantu mengelola path secara otomatis sesuai dengan context path aplikasi.
  Jadi lebih aman dan fleksibel. Jika file CSS tidak ada, halaman tetap bisa dibuka tetapi CSS tidak diterapkan karena file tidak ditemukan (404).

### Pertanyaan Refleksi
1. Kalau kita pakai fragment, navbar dan footer cukup dibuat satu kali saja, misalnya di file fragments/navbar.
   html dan fragments/footer.html.Lalu di halaman lain tinggal dipanggil.
   Kenapa ini enak?
    - Tidak perlu copy–paste navbar di setiap halaman
    - Kalau mau ubah menu, cukup ubah di satu tempat
    - Kode jadi lebih rapi dan tidak berulang-ulang
    - Lebih mudah dirawat (maintenance)
2.  - Folder templates/ itu untuk file HTML yang diproses oleh Thymeleaf. Jadi biasanya dipakai untuk halaman yang ada datanya dari Controller.
- Sedangkan folder static/ itu untuk file biasa seperti CSS, JavaScript, dan gambar. File di dalam static/ bisa langsung dibaca oleh browser.
- CSS ada di static/ karena CSS itu cuma file tampilan biasa, tidak perlu diproses oleh Thymeleaf
3.  - th:replace itu artinya mengganti tag yang ada dengan isi fragment. Jadi tag lama hilang, diganti isi yang baru.
- th:insert itu artinya memasukkan isi fragment ke dalam tag yang sudah ada. Jadi tag lamanya tetap ada.
  Sederhananya:
  th:replace → diganti total
  th:insert → dimasukkan ke dalam
4. Karena @{} itu otomatis menyesuaikan dengan alamat aplikasi (context path).
   Kalau nanti alamat aplikasi berubah, URL tetap aman dan tidak error.
   Jadi lebih fleksibel dan lebih aman dibanding tulis langsung /products misalnya.
5. Kalau langsung pakai new, memang bisa jalan, tapi itu bukan cara yang benar di Spring.
   Kalau pakai Dependency Injection (DI), Spring yang akan mengatur dan membuat object-nya. Itu lebih rapi, lebih mudah diatur, dan lebih mudah untuk testing.
   Jadi lebih baik pakai DI daripada buat object sendiri di dalam Controller.