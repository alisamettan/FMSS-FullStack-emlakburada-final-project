EmlakBurada-FMSS FullStack Bitirme Projesi
Bu proje fullstack bir emlak sitesi projesidir.

-Kullanıcılar kayıt olabilir.
-Email ve şifresi ile giriş yapabilir.Giriş yapınca token oluşturulur.Bu token ile yetkili olduğu işlemleri gerçekleştirebilir.
-Kayıt olmayan kullanıcı sitede başka kullanıcılar tarafından yayınlanan ilanları görebilir.
-Kayıt olan kullanıcı ilan yayınlayabilir ve kendi ilanlarını silip,güncelleyebilir.
-Her kullanıcı kayıt olduktan sonra ilan yayınlama hakkı "0"dır.İlan yayınlaması için sistemde sitede bulunan paketlerden herhangi birisini satın alması gerekir.
-Satın alma işlemi giriş yapan kullanıcının id'sine göre yapılır.
-Üç farklı paket tanımlanmıştır: ilki, 10 ilan hakkı sağlar; ikincisi, 20 ilan hakkı sağlar; üçüncüsü ise 20 ilan hakkı sağlar ve yayınladığı ilanları özel bir çerçevede ve vurgulanmış olarak gösterir.
-Paket satın alındıktan sonra kullanım süresi 30 gündür.
-Kullanıcı ilan oluşturmak istediğinde, ilan hakları ve kullanım süresi kontrol edilir. Paketin süresi geçmişse ya da ilan hakkı yoksa, ilan yayınlama işlemi iptal olur. Kullanıcıya hata mesajı döner. 
Kullanım süresi geçmemişse ve ilan hakkı varsa ilan oluşturulur. Kullanıcının bakiyesinden düşülür.
-İlan oluşturulduktan sonra IN_REVIEW statüsündedir ve anasayfadaki bu ilanların ilan detayına gidilemez.İlanı yayınlayan kullanıcı kendi profilinden bu ilanların statüsünü ACTIVE veya PASSIVE olarak değiştirebilirler.
ACTIVE olan ilanlar anasayfada gözükür ve ilan detayına gidilebilir.PASSIVE olan ilanlar anasayfada gözükmez.Statu değişikliği rabbitMQ üzerinden yapılmaktadır yani değişiklik olduğunda değişiklik queue'ya kaydedilir ve bir başka serviste
dinlenip database'se kaydedilir.Böylelikle statu değişikliği asenkron yapılır.
-Kullanıcı kendi profilinde kaç adet ve kaç günlük ilan yayınlama hakkı kaldığını görebilir.
-Sitede pagination,searching ve sorting kullanılmıştır.Sayfalarda ileri geri geçişler yapılabilir,ilanın title'ına göre arama yapılabilir ve fiyatına göre sıralama yapılabilir.

UML Diagram
<img width="629" alt="Screenshot 2024-07-21 at 18 19 21" src="https://github.com/user-attachments/assets/a24add22-d96c-404a-b0dd-190887207bdd">

-6 adet microservice bulunmaktadır.Hepsi feignclient ile birleştirilmiş olup iletişim halindedir.
-5 microservice gateway'den yönetilmektedir.NotificationConsumer service ise;kullanıcı kayıt olduğunda,kullanıcı bilgileri rabbitMq ile gönderilip
burada dinlenip kullanıcın mail adresine bir hoşgeldin maili göndermekte kullanılmaktadır.

API Kullanımı 

Postman Api Dökümantasyonu:
https://documenter.getpostman.com/view/32606553/2sA3kUHNYm#08dc227a-32ed-42de-becb-e83efc914d89

Swagger Api Dokümantasyonu:

<img width="1468" alt="Screenshot 2024-07-21 at 17 26 10" src="https://github.com/user-attachments/assets/76247aa1-324f-440b-bf65-704ff75583b8">

<img width="1465" alt="Screenshot 2024-07-21 at 17 26 36" src="https://github.com/user-attachments/assets/3c66d656-d184-407e-89fc-f82a9595fec1">

<img width="1462" alt="Screenshot 2024-07-21 at 17 26 44" src="https://github.com/user-attachments/assets/27ebd7fd-76d6-4d0a-804d-129c5aa2fc76">

<img width="1457" alt="Screenshot 2024-07-21 at 17 26 53" src="https://github.com/user-attachments/assets/0e800872-e245-4ecf-b7f8-d17f8e77aca0">


Coverage

User

![Screenshot 2024-07-21 at 18 51 23](https://github.com/user-attachments/assets/da3575f6-1c22-4527-aa6d-72c5759bd42a)

Advert

![Screenshot 2024-07-21 at 18 51 38](https://github.com/user-attachments/assets/183a1612-8fc4-4687-a24b-854dd5ceeaa3)

AdvertStatus

![Screenshot 2024-07-21 at 18 51 52](https://github.com/user-attachments/assets/bec2d87b-3503-4268-8ba4-f64495eb959f)

Package

![Screenshot 2024-07-21 at 18 52 05](https://github.com/user-attachments/assets/38608060-7294-44cb-9973-1458ce253fde)

Order 

![Screenshot 2024-07-21 at 18 52 16](https://github.com/user-attachments/assets/ced9d248-2e88-4de4-8d83-63a3e127ff82)

Kullanılan Teknolojiler

Backend:
-Service Discovery
-Open Feign
-RabbitMQ
-Lombok
-PostgreSQL
-Spring Boot
-Hibernate
-Spring Data Jpa
-Spring Specification
-Spring Security
-Spring Email

Frontend:
-React.js
-Next.js 14
-App router
-Tailwind
-cookies()
-Server Side Rendering
-Client Side Rendering
-React Hook form

Teşekkürler
-Bize eğitim boyunca önemli bilgiler veren,sektörden örnekler sunan eğitmenlerimiz Cem DİRMAN ve Çağlayan YANIKOĞLU'a emekleri için çok teşekkür ederim.
-Bootcamp için FMSS ve Patika.dev'e teşekkür ederim.





