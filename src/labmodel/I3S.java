package labmodel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import byransha.DB;
import labmodel.model.v0.ACMClassifier;
import labmodel.model.v0.Lab;
import labmodel.model.v0.Nationality;
import labmodel.model.v0.ResearchGroup;
import labmodel.model.v0.Structure;
import toools.io.file.RegularFile;
import toools.text.csv.CSV;

/*
 * https://codimd.math.cnrs.fr/_ivy9aRUQK2o4ue-p9RHKg?both
 * https://cran.r-project.org/web/classifications/ACM.html
 */

public class I3S extends Lab {

	static File inputDir = new RegularFile("~/perso/local_files/job/i3s/tableau_de_bord/input_sources").toFile();

	public static List<Nationality> importNationalities() throws IOException {
		return Files.readAllLines(new File(inputDir, "CH_Nationality_List_20171130_v1.csv").toPath()).stream()
				.map(l -> new Nationality()).toList();
	}

	public static void main(String[] args) throws IOException {
		DB.instance.delete();
		Lab i3s = new Lab();
		i3s.name.set("I3S");

		Files.readAllLines(Path.of(inputDir.getAbsolutePath(), "poles.lst")).forEach(l -> {
			Structure pole = new ResearchGroup();
			pole.name.set(l);
			i3s.subStructures.add(pole);
		});

		i3s.bfs(c -> System.out.println("* : " + c));
		i3s.bfs(c -> DB.instance.accept(c));
		System.out.println(i3s.subStructures);
		System.out.println(DB.instance.countNodes());
		DB.instance.saveAll(DB.instance.sysoutPrinter);

		System.exit(0);

		var acm = CSV.disassemble(Files.readString(Path.of(inputDir.getAbsolutePath(), "acm_classification.txt")), ";")
				.stream().map(l -> new ACMClassifier(l.get(0), l.get(1))).toList();


	}

}
