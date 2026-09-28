package pertemuan4;

import java.util.ArrayList;

public class Rekening {
	private String nomorRekening;
	private String namaPemilik;
	private String pin;
	
	protected double saldo;
	protected ArrayList<Transaksi> riwayatTransaksi;
	
	private boolean diblokir;
	private int percobaanPinGagal;
	
	public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;
		
		if (pinAwal.length() == 6) {
			this.pin = pinAwal;
		} else {
			System.out.println("Peringatan: PIN harus 6 digit!"
					+ " Menggunakan PIN default 123456");
			this.pin = "123456";
		}
		
		this.riwayatTransaksi = new ArrayList<>();
		
		this.diblokir = false;
		this.percobaanPinGagal = 0;
		
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat dengan saldo Rp" + saldo);
	}
	
	public String getNomorRekening() { return nomorRekening; }
	public String getNamaPemilik() { return namaPemilik; }

	public boolean otentikasi(String inputPin) {
		if (diblokir) {
			System.out.println("Rekening telah diblokir!");
			return false;
		}
		
		if (this.pin.equals(inputPin)) {
			percobaanPinGagal = 0;
			return true;
		} else {
			percobaanPinGagal++;
			if (percobaanPinGagal >= 3) {
				diblokir = true;
				System.out.println("Rekening diblokir karena PIN salah 3 kali!");
			}
			return false;
		}
	}
	
	public void setorTunai(double nominal) {
		if (diblokir) {
			System.out.println("Transaksi ditolak! Rekening telah terblokir.");
			return;
		}
		
		if (nominal > 0) {
			saldo += nominal;
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
			riwayatTransaksi.add(trxBaru);
			System.out.println("Setor tunai Rp" + nominal
					+ " berhasil. Saldo saat ini: Rp" + saldo);
		} else {
			System.out.println("Gagal: Nominal setor harus lebih dari 0!");
		}
	}
	
	public void tarikTunai(Double nominal) {
		if (diblokir) {
			System.out.println("Transaksi ditolak! Rekening telah terblokir.");
			return;
		}
		
		if (nominal < 10000) {
			System.out.println("Transaksi Gagal: Minimal nominal penarikan 10.000!");
		} else if (nominal > saldo) {
			System.out.println("Transaksi Gagal: Saldo tidak mencukupi.");
			System.out.println("Saldo Anda: Rp" + nominal);
		} else {
			saldo -= nominal;
			System.out.println("Transaksi Berhasil!");
			
			String idTrx = "TRX-T-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Debit", nominal);
			riwayatTransaksi.add(trxBaru);
			
			System.out.println("Sisa saldo: Rp" + saldo);
		}
	}

	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik : " + namaPemilik);
		System.out.println("Saldo Akhir  : Rp" + saldo);
		System.out.println("---------------------");
	}
	
	public void cetakMutasi() {
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Belum ada transaksi pada rekening ini.");
		} else {
			for (Transaksi trx : riwayatTransaksi)
				trx.cetakDetail();
		}
	}
}
