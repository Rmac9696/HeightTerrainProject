package HeightTerrian
import com.jme3.app.SimpleApplication

import com.jme3.bullet.BulletAppState
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape
import com.jme3.bullet.collision.shapes.CollisionShape
import com.jme3.bullet.control.CharacterControl
import com.jme3.bullet.control.RigidBodyControl
import com.jme3.bullet.util.CollisionShapeFactory
import com.jme3.input.KeyInput
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.KeyTrigger
import com.jme3.light.DirectionalLight
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.{Geometry, Node, Spatial}
import com.jme3.system.AppSettings
import com.jme3.terrain.geomipmap.TerrainQuad
import com.jme3.texture.{Texture, Texture2D}


object Main extends SimpleApplication with ActionListener {
  private var sceneModel: Spatial = new Node()
  private var player = new CharacterControl()
  private val walkDirection = new Vector3f()
  private var left = false
  private var right = false
  private var up = false
  private var down = false

  private val camDir = new Vector3f()
  private val camLeft = new Vector3f()

  def main(args: Array[String]): Unit= {
    val settings = new AppSettings(true)
    settings.setFrameRate(120)
    this.setSettings(settings)
    this.start()
  }
  override def simpleInitApp(): Unit =  {
    val bulletAppState = new BulletAppState()
    stateManager.attach(bulletAppState)
    viewPort.setBackgroundColor(new ColorRGBA(0.7f,0.8f,1f,1f))
    flyCam.setMoveSpeed(100)

    setUpKeys()
    setUpLight()

    //assetManager.registerLocator("J:\\Jmonkey tutorials\\HelloCollision-SDK\\HelloCollision\\assets\\town.zip", classOf[ZipLocator])
    //sceneModel = assetManager.loadModel("main.scene")
    var heightMapper:HeightMapper = new HeightMapper {
      override def getHeight(x: Float, y: Float): Float = 0f
    }
    heightMapper = heightMapper.noise().scale(0.01f).mul(50)

    val hmap = heightMapper.toHeightMap(0f,0f,513)

    val level = new TerrainQuad("terrain",65,513,hmap)

    val mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md")
    mat.setTexture("DiffuseMap",assetManager.loadTexture("Textures/Grass2.png"))
    mat.setTexture("NormalMap",assetManager.loadTexture("Textures/Grass_N2.png"))
    mat.setBoolean("UseMaterialColors",true)
    mat.setColor("Diffuse",ColorRGBA.White)
    mat.setColor("Specular",ColorRGBA.White)
    mat.setFloat("Shininess",8f)

    level.setMaterial(mat)
    sceneModel = level
    sceneModel.setLocalScale(2f)
    val sceneShape:CollisionShape = CollisionShapeFactory.createMeshShape(sceneModel)
    val landscape = new RigidBodyControl(sceneShape,0)
    sceneModel.addControl(landscape)

    val capsuleShape = new CapsuleCollisionShape(1.5f,6f,1)
    player = new CharacterControl(capsuleShape,0.05f)
    player.setJumpSpeed(20)
    player.setFallSpeed(30)
    rootNode.attachChild(sceneModel)
    bulletAppState.getPhysicsSpace.add(landscape)
    bulletAppState.getPhysicsSpace.add(player)

    player.setGravity(30f)
    player.setPhysicsLocation(new Vector3f(0,10.0f,0))
  }

  override def onAction(name: String, isPressed: Boolean, tpf: Float): Unit = {
    name match {
      case "Left" => left = isPressed
      case "Right" => right= isPressed
      case "Up" => up= isPressed
      case "Down" => down=isPressed
      case "Jump" => if (isPressed) player.jump()
    }
  }

  override def simpleUpdate(tpf: Float): Unit = {
    super.simpleUpdate(tpf)
    camDir.set(cam.getDirection.multLocal(0.6f))
    camLeft.set(cam.getLeft.multLocal(0.4f))
    walkDirection.set(0,0,0)
    if(left) walkDirection.addLocal(camLeft)
    if(right) walkDirection.addLocal(camLeft.negate())
    if(up)walkDirection.addLocal(camDir)
    if(down)walkDirection.addLocal(camDir.negate())
    player.setWalkDirection(walkDirection)
    cam.setLocation(player.getPhysicsLocation())
  }

  def setUpKeys( ): Unit= {
    inputManager.addMapping("Left",new KeyTrigger(KeyInput.KEY_A))
    inputManager.addMapping("Right",new KeyTrigger(KeyInput.KEY_D))
    inputManager.addMapping("Up",new KeyTrigger(KeyInput.KEY_W))
    inputManager.addMapping("Down",new KeyTrigger(KeyInput.KEY_S))
    inputManager.addMapping("Jump",new KeyTrigger(KeyInput.KEY_SPACE))

    inputManager.addListener(this,"Left")
    inputManager.addListener(this,"Right")
    inputManager.addListener(this,"Up")
    inputManager.addListener(this,"Down")
    inputManager.addListener(this,"Jump")
  }

  def setUpLight(): Unit= {
    //val al = new AmbientLight()
    //al.setColor(ColorRGBA.White.mult(1.3f))
    //rootNode.addLight(al)
    val dl = new DirectionalLight()
    dl.setColor(ColorRGBA.White)
    dl.setDirection(new Vector3f(0.2f,-0.6f, -0.5f).normalize())
    rootNode.addLight(dl)
  }
}
