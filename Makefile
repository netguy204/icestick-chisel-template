# assume that everything in the source directory is synthesizable and
# should be synthesized into the final product. Replace this glob with
# a list of files otherwise.
CHISEL_SOURCE = $(wildcard src/*.scala)
VERILOG_SOURCE = generated/src/Main.v

# by default, run the tests

default: src/Main.out

# but we can also generate the program file
upload: generated/main.bin
	iceprog $<

generated/main.bin: generated/main.txt
	icepack $< $@

generated/main.txt: generated/main.blif src/icestick.pcf
	arachne-pnr -d 1k -p src/icestick.pcf $< -o $@

generated/main.blif: $(VERILOG_SOURCE)
	yosys -p "synth_ice40 -blif $@" $(VERILOG_SOURCE)

generated/src/%.v: src/%.scala
	cd src && make $(patsubst src/%.scala, %.v, $<)

src/%.out: src/%.scala
	cd src && make $(patsubst src/%.scala, %.out, $<)

clean:
	cd src && make clean
	rm -rf generated

distclean: clean
	rm -rf src/project
