package br.com.app.smart.business.service;

import java.io.File;
import java.util.Date;
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

import br.com.app.smart.business.conta.dto.AssinaturaDTO;
import br.com.app.smart.business.conta.dto.ContaDTO;
import br.com.app.smart.business.conta.dto.ContratoDTO;
import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.databuilder.AssinaturaBuilder;
import br.com.app.smart.business.databuilder.AssinaturaBuilder.TipoAssinaturaBuilder;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.util.ClassUtil;
import br.com.app.smart.business.util.PackageUtil;

/**
 * @author daniel-matos
 *
 */
@RunWith(Arquillian.class)
public class AssinaturaServiceImpTest {

	@Deployment
	public static Archive<?> createTestArchive() {

		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");

		File[] libs = pom.resolve("br.com.app.smart.business:app-smart-business-common:0.0.1-SNAPSHOT")
				.withClassPathResolution(true).withTransitivity().asFile();

		File[] libs2 = pom.resolve("org.modelmapper:modelmapper:0.7.5").withClassPathResolution(true).withTransitivity()
				.asFile();
		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
				.addAsLibraries(libs)
				.addAsLibraries(libs2)
				.addClasses(ClassUtil.getClassInfraDAO())
				.addPackage(PackageUtil.DATA_BUILDER.getPackageName())
				.addPackage(PackageUtil.BUILDER_INFRA.getPackageName())
				.addPackage(PackageUtil.CONVERSORES.getPackageName())
				.addPackage(PackageUtil.FACEDE.getPackageName())
				.addPackage(PackageUtil.EXCEPTION.getPackageName())
				.addPackage(PackageUtil.MODEL.getPackageName())
				.addPackage(PackageUtil.SERVICE.getPackageName())
				.addPackage(PackageUtil.UTIL.getPackageName())
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");

		System.out.println(war.toString(true));

		return war;
	}

	@EJB(beanName = "AssinaturaServiceImp", beanInterface = IServicoRemoteDAO.class)
	private IServicoRemoteDAO<AssinaturaDTO> assinaturaServiceImp;
	
	@EJB(beanName = "ContaServiceImp", beanInterface = IServicoRemoteDAO.class)
	private IServicoRemoteDAO<ContaDTO> contaServiceImp;
	
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

			System.out.println("Test:Crud");
			List<AssinaturaDTO> assinaturas = assinaturaServiceImp.bustarTodos();
			for (AssinaturaDTO assinaturaDTO : assinaturas) {
				assinaturaServiceImp.remover(assinaturaDTO);
			}

			AssinaturaDTO dto = AssinaturaBuilder.getInstanceDTO(TipoAssinaturaBuilder.INSTANCIA);
			AssinaturaDTO dto2 = AssinaturaBuilder.getInstanceDTO(TipoAssinaturaBuilder.INSTANCIA);

			dto = assinaturaServiceImp.adiconar(dto);

			AssinaturaDTO resutaldoBusca = assinaturaServiceImp.bustarPorID(dto.getId());
			Assert.assertNotNull(resutaldoBusca);
			Assert.assertEquals(dto.getId().longValue(), resutaldoBusca.getId().longValue());

			dto2 = assinaturaServiceImp.adiconar(dto2);
			AssinaturaDTO resutaldoBusca2 = assinaturaServiceImp.bustarPorID(dto2.getId());
			Assert.assertNotNull(resutaldoBusca2);

			Assert.assertEquals(dto2.getId().longValue(), resutaldoBusca2.getId().longValue());

			List<AssinaturaDTO> todos = assinaturaServiceImp.bustarTodos();
			Assert.assertNotNull(todos);
			Assert.assertTrue(todos.size() == 2);

			int range[] = { 0, 2 };
			List<AssinaturaDTO> todosIntervalo = assinaturaServiceImp.bustarPorIntervaloID(range);
			Assert.assertNotNull(todosIntervalo);
			Assert.assertTrue(todosIntervalo.size() == 2);

			resutaldoBusca2.getRegistroAuditoria().setDataAlteracao(new Date());;

			AssinaturaDTO resutaldoBusca3 = assinaturaServiceImp.alterar(resutaldoBusca2);
			Assert.assertEquals(resutaldoBusca2.getRegistroAuditoria().getDataAlteracao(), resutaldoBusca3.getRegistroAuditoria().getDataAlteracao());

			assinaturaServiceImp.remover(resutaldoBusca3);

			AssinaturaDTO dto4 = assinaturaServiceImp.bustarPorID(resutaldoBusca3.getId());
			Assert.assertNull(dto4);

		} catch (InfraEstruturaException e) {
			e.printStackTrace();
		} catch (NegocioException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void inserirRegistrosIguais() throws InfraEstruturaException, NegocioException {
		System.out.println("Test:inserirRegistrosIguais");
		AssinaturaDTO dto = AssinaturaBuilder.getInstanceDTO(TipoAssinaturaBuilder.INSTANCIA);
		System.out.println(dto);
		System.out.println(this.assinaturaServiceImp);
		System.out.println(this.assinaturaServiceImp.getClass());
		dto = this.assinaturaServiceImp.adiconar(dto);
		thrown.expect(InfraEstruturaException.class);
		this.assinaturaServiceImp.adiconar(dto);

	}

	@Test
	public void buscarIdNaoExistente() {

		try {
			System.out.println("Test:buscarIdNaoExistente");
			AssinaturaDTO dto = this.assinaturaServiceImp.bustarPorID(100L);
			Assert.assertNull(dto);

		} catch (InfraEstruturaException e) {
			e.printStackTrace();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void criarNovaAssinaturaContratoConta() {

		try {

			System.out.println("Test:criarNovaAssinaturaContratoConta");
			List<AssinaturaDTO> assinaturas = assinaturaServiceImp.bustarTodos();
			for (AssinaturaDTO assinaturaDTO : assinaturas) {
				assinaturaServiceImp.remover(assinaturaDTO);
			}

			AssinaturaDTO dto = AssinaturaBuilder.getInstanceDTO(TipoAssinaturaBuilder.ASSINATURA_CONTRATO_CONTA);
			AssinaturaDTO dto2 = AssinaturaBuilder.getInstanceDTO(TipoAssinaturaBuilder.ASSINATURA_CONTRATO_CONTA);

			List<ContaDTO> contas = this.contaServiceImp.adiconar(dto.getContas());
			List<ContratoDTO> contratos = this.contratoServiceImp.adiconar(dto.getContratos());
			dto.setContas(contas);
			dto.setContratos(contratos);
			
			dto = assinaturaServiceImp.adiconar(dto);
			
			
			List<ContaDTO> contas2 = this.contaServiceImp.adiconar(dto2.getContas());
			List<ContratoDTO> contratos2 = this.contratoServiceImp.adiconar(dto2.getContratos());
			dto2.setContas(contas2);
			dto2.setContratos(contratos2);
			
			dto2= assinaturaServiceImp.adiconar(dto2);

			AssinaturaDTO resutaldoBusca = assinaturaServiceImp.bustarPorID(dto.getId());
			Assert.assertNotNull(resutaldoBusca);
			Assert.assertEquals(dto.getId().longValue(), resutaldoBusca.getId().longValue());

			dto2 = assinaturaServiceImp.adiconar(dto2);
			AssinaturaDTO resutaldoBusca2 = assinaturaServiceImp.bustarPorID(dto2.getId());
			Assert.assertNotNull(resutaldoBusca2);

			Assert.assertEquals(dto2.getId().longValue(), resutaldoBusca2.getId().longValue());
			
			Assert.assertNotNull(dto.getContas());
			Assert.assertNotNull(dto.getContratos());
			
			Assert.assertEquals(dto.getContas().get(0).getId(), contas.get(0).getId());
			Assert.assertEquals(dto.getContratos().get(0).getId(), contratos.get(0).getId());
			
			Assert.assertEquals(dto2.getContas().get(0).getId(), contas2.get(0).getId());
			Assert.assertEquals(dto2.getContratos().get(0).getId(), contratos2.get(0).getId());

			List<AssinaturaDTO> todos = assinaturaServiceImp.bustarTodos();
			Assert.assertNotNull(todos);
			Assert.assertTrue(todos.size() == 2);

			int range[] = { 0, 2 };
			List<AssinaturaDTO> todosIntervalo = assinaturaServiceImp.bustarPorIntervaloID(range);
			Assert.assertNotNull(todosIntervalo);
			Assert.assertTrue(todosIntervalo.size() == 2);

			resutaldoBusca2.getRegistroAuditoria().setDataAlteracao(new Date());;

			AssinaturaDTO resutaldoBusca3 = assinaturaServiceImp.alterar(resutaldoBusca2);
			Assert.assertEquals(resutaldoBusca2.getRegistroAuditoria().getDataAlteracao(), resutaldoBusca3.getRegistroAuditoria().getDataAlteracao());

			assinaturaServiceImp.remover(resutaldoBusca3);

			AssinaturaDTO dto4 = assinaturaServiceImp.bustarPorID(resutaldoBusca3.getId());
			Assert.assertNull(dto4);

		} catch (InfraEstruturaException e) {
			e.printStackTrace();
		} catch (NegocioException e) {
			e.printStackTrace();
		}

	}


}
