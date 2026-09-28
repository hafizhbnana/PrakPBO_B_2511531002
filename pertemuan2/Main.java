package pertemuan2;
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
		
		Rekening rekeningBaru = new Rekening(no, nama, saldo);
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
					System.out.println("Masukkan nominal setor: ");
					double setor = input.nextDouble();
					akunAktif.setorTunai(setor);
				}
				break;
			case 3:
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
				} else {
					System.out.println("Masukkan nominal tarik: ");
					double tarik = input.nextDouble();
					akunAktif.tarikTunai(tarik);
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
				akunAktif.cetakMutasi();
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
