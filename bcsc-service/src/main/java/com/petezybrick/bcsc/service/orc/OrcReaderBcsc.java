package com.petezybrick.bcsc.service.orc;

import java.util.List;


public class OrcReaderBcsc {
	public static void main(String[] args) throws Exception {
		OrcReaderBcsc orcReaderBcsc = new OrcReaderBcsc();
		orcReaderBcsc.process();
	}

	public void process() throws Exception {
		final PersonOrcVo personOrcVo = new PersonOrcVo();
		List<BaseOrcVo> personOrcVos = OrcCommon.read("/tmp/person.orc", personOrcVo);
		personOrcVos.forEach(pov -> System.out.println((PersonOrcVo) pov));
	}

}
