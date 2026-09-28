package pertemuan1;

public class Rekening {
	String nomorRekening;
	String namaPemilik;
	double saldo;

	public Rekening(String nomor, String nama, double saldoAwal) {
		nomorRekening = nomor;
		namaPemilik = nama;
		saldo = saldoAwal;
		System.out.println("Rekening atas nama " + namaPemilik
				+ " berhasil dibuat dengan saldo Rp" + saldo);
	}
	
	public String getNomorRekening() {
		return nomorRekening;
	}

	public void setorTunai(double nominal) {
		if (nominal > 0) {
			saldo += nominal;
			System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
		} else {
			System.out.println("Gagal: Nominal setor harus lebih dari 0!");
		}
	}
	
	public void tarikTunai(Double nominal) {
		if (nominal < 10000) {
			System.out.println("Transaksi Gagal: Minimal nominal penarikan 10.000!");
		} else if (nominal > saldo) {
			System.out.println("Transaksi Gagal: Saldo tidak mencukupi.");
			System.out.println("Saldo Anda: Rp" + nominal);
		} else {
			saldo -= nominal;
			System.out.println("Transaksi Berhasil!");
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
}
