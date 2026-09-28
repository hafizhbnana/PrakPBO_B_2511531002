package pertemuan4;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
	
	public static void bukaRekening(ArrayList<Rekening> list, Scanner input) {
		System.out.print("Masukkan No Rekening: ");
		String no = input.nextLine();
		System.out.print("Masukkan Nama Pemilik: ");
		String nama = input.nextLine();
		System.out.print("Masukkan Saldo Awal: ");
		double saldo = input.nextDouble();
		input.nextLine();
		System.out.print("Masukkan PIN (6 digit): ");
		String pin = input.nextLine();
		System.out.println("Pilih Produk: 1. Tabungan Umum | 2. Giro Bisnis");
		System.out.print("Pilihan: ");
		int produk = input.nextInt();
		
		Rekening rekeningBaru;
		
		if (produk == 1) {
			System.out.print("Masukkan suku bunga (%): ");
			double sukuBunga = input.nextDouble();
			
			rekeningBaru = new RekeningTabungan(
				no, nama, saldo, pin, sukuBunga
			);
		} else if (produk == 2) {
			System.out.print("Masukkan batas overdraft: ");
			double batasOverdraft = input.nextDouble();
			
			rekeningBaru = new RekeningGiro(
				no, nama, saldo, pin, batasOverdraft
			);
		} else {
			System.out.println("Pilihan produk tidak valid!");
			return;
		}
		
		input.nextLine();
		list.add(rekeningBaru);
		System.out.println("Rekening Berhasil Dibuat!");
	}
	
	public static Rekening gantiAkun(ArrayList<Rekening> list, Scanner input) {
		System.out.print("Masukkan No Rekening: ");
		String no = input.nextLine();
		for (Rekening rekening : list) {
			if (rekening.getNomorRekening().equals(no)) {
				System.out.println("Berhasil berganti akun!");
				return rekening;
			}
		}
		System.out.println("Error: Rekening tidak ditemukan!");
		return null;
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening> daftarRekening = new ArrayList<>();
		Rekening akunAktif = null;
		boolean isRunning = true;
		
		System.out.println("=== SISTEM PERBANKAN MINI ===");
		
		while (isRunning) {
			System.out.println("\nMenu Utama:");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Akun");
			System.out.println("6. Cetak Mutasi (Riwayat)");
			System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
			System.out.println("0. Keluar");
			System.out.print("Pilih menu: ");
			
			int pilihan = input.nextInt();
			input.nextLine();
			
			switch (pilihan) {
			case 1:
				bukaRekening(daftarRekening, input);
				akunAktif = daftarRekening.get(daftarRekening.size() - 1);
				break;
			case 2:
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
				} else {
					System.out.print("Masukkan nominal setor: ");
					double setor = input.nextDouble();
					akunAktif.setorTunai(setor);
				}
				break;
			case 3:
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
				} else {
					System.out.print("Masukkan PIN: ");
					String pin = input.nextLine();
					
					if (akunAktif.otentikasi(pin)) {
						System.out.print("Masukkan nominal tarik: ");
						double tarik = input.nextDouble();
						input.nextLine();
						
						akunAktif.tarikTunai(tarik);
					} else {
						System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
					}
				}
				break;
			case 4:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				} else {
					akunAktif.cekInformasi();
				}
				break;
			case 5:
				Rekening akunBaru = gantiAkun(daftarRekening, input);
				if (akunBaru != null) akunAktif = akunBaru;
				break;
			case 6:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				} else {
					System.out.print("Masukkan PIN: ");
					String pin = input.nextLine();
					
					if (akunAktif.otentikasi(pin)) {
						akunAktif.cetakMutasi();
					} else {
						System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
					}
				}
				break;
			case 7:
				if (akunAktif instanceof RekeningTabungan) {
					RekeningTabungan tabungan = (RekeningTabungan) akunAktif;
					tabungan.tambahBungaAkhirBulan();
				} else {
					System.out.println( "Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
				}
				break;
			case 0:
				isRunning = false;
				System.out.println("Sistem ditutup. Terima kasih!");
				break;
			default:
				System.out.println("Pilihan tidak valid!");
			}
		}
		input.close();
	}
}
