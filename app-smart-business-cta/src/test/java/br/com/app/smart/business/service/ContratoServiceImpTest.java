package br.com.app.smart.business.service;

import java.io.File;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import br.com.app.smart.business.conta.dto.ContratoDTO;
import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.databuilder.ContratoBuilder;
import br.com.app.smart.business.databuilder.ContratoBuilder.TipoContratoBuilder;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.util.ClassUtil;
import br.com.app.smart.business.util.PackageUtil;

/**
 * @author daniel-matos
 *
 */
@RunWith(Arquillian.class)
public class ContratoServiceImpTest {

	@Deployment
	public static Archive<?> createTestArchive() {

		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");

		File[] libs = pom.resolve("br.com.app.smart.business:app-smart-business-common:0.0.1-SNAPSHOT")
				.withClassPathResolution(true).withTransitivity().asFile();

		File[] libs2 = pom.resolve("org.modelmapper:modelmapper:0.7.5").withClassPathResolution(true).withTransitivity()
				.asFile();
		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war").addAsLibraries(libs).addAsLibraries(libs2)
				.addClasses(ClassUtil.getClassInfraDAO())
				.addClasses(ClassUtil.getClassInfraDAO())
				.addPackage(PackageUtil.DATA_BUILDER.getPackageName())
				.addPackage(PackageUtil.BUILDER_INFRA.getPackageName())
				.addPackage(PackageUtil.CONVERSORES.getPackageName()).addPackage(PackageUtil.ENUMS.getPackageName())
				.addPackage(PackageUtil.EXCEPTION.getPackageName()).addPackage(PackageUtil.MODEL.getPackageName())
				.addPackage(PackageUtil.SERVICE.getPackageName()).addPackage(PackageUtil.UTIL.getPackageName())
				.addPackage(PackageUtil.FACEDE.getPackageName()).addPackage(PackageUtil.DATA.getPackageName())
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");

		System.out.println(war.toString(true));

		return war;
	}

	@EJB(beanName = "ContratoServiceImp", beanInterface = IServicoRemoteDAO.class)
	private IServicoRemoteDAO<ContratoDTO> contratoServiceImp;


	@Rule
	public ExpectedException thrown = ExpectedException.none();

	/**
	 * 
	 * @see a) processo define-se em criar sempre persisitir tres registros b)
	 *      buscar os dois registros e verificar se os dois novos ids c) alterar
	 *      os dois registros e verificar se esses registros foram d) excluir
	 *      todos regristros
	 */
	@Test
	public void crud() {

		try {

		
			List<ContratoDTO> contratos = contratoServiceImp.bustarTodos();
			for (ContratoDTO contratoDTO : contratos) {
				contratoServiceImp.remover(contratoDTO);
			}

			ContratoDTO dto = ContratoBuilder.getInstanceDTO(TipoContratoBuilder.INSTANCIA);
			ContratoDTO dto2 = ContratoBuilder.getInstanceDTO(TipoContratoBuilder.INSTANCIA);

			dto = contratoServiceImp.adiconar(dto);

			ContratoDTO resutaldoBusca = contratoServiceImp.bustarPorID(dto.getId());
			Assert.assertNotNull(resutaldoBusca);
			Assert.assertEquals(dto.getId().longValue(), resutaldoBusca.getId().longValue());

			dto2 = contratoServiceImp.adiconar(dto2);
			ContratoDTO resutaldoBusca2 = contratoServiceImp.bustarPorID(dto2.getId());
			Assert.assertNotNull(resutaldoBusca2);

			Assert.assertEquals(dto2.getId().longValue(), resutaldoBusca2.getId().longValue());

			List<ContratoDTO> todos = contratoServiceImp.bustarTodos();
			Assert.assertNotNull(todos);
			Assert.assertTrue(todos.size() == 2);

			int range[] = { 0, 2 };
			List<ContratoDTO> todosIntervalo = contratoServiceImp.bustarPorIntervaloID(range);
			Assert.assertNotNull(todosIntervalo);
			Assert.assertTrue(todosIntervalo.size() == 2);

			resutaldoBusca2.setClausula("alterado");

			ContratoDTO resutaldoBusca3 = contratoServiceImp.alterar(resutaldoBusca2);
			Assert.assertEquals(resutaldoBusca2.getClausula(), resutaldoBusca3.getClausula());

			contratoServiceImp.remover(resutaldoBusca3);

			ContratoDTO dto4 = contratoServiceImp.bustarPorID(resutaldoBusca3.getId());
			Assert.assertNull(dto4);

		} catch (InfraEstruturaException e) {
			e.printStackTrace();
		} catch (NegocioException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void inserirRegistrosIguais() throws InfraEstruturaException, NegocioException {
		ContratoDTO dto = ContratoBuilder.getInstanceDTO(TipoContratoBuilder.INSTANCIA);
		System.out.println(dto);
		System.out.println(this.contratoServiceImp);
		System.out.println(this.contratoServiceImp.getClass());
		dto = this.contratoServiceImp.adiconar(dto);
		thrown.expect(InfraEstruturaException.class);
		this.contratoServiceImp.adiconar(dto);

	}

	@Test
	public void buscarIdNaoExistente() {

		try {
			ContratoDTO dto = this.contratoServiceImp.bustarPorID(100L);
			Assert.assertNull(dto);

		} catch (InfraEstruturaException e) {
			e.printStackTrace();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
