package production;

import java.awt.Color;

import cobweb.Agent;
import cobweb.Environment.Location;
import cobweb.globals;
import cwcore.ComplexAgent;
import cwcore.ComplexEnvironment.Drop;

public class Product implements Drop {
	private final ProductionMapper productionMapper;
	final Location loc;

	Product(float value, Agent owner, Location loc, ProductionMapper productionMapper) {
		this.value = value;
		this.owner = owner;
		this.loc = loc;
		this.productionMapper = productionMapper;
	}

	Agent owner;
	private float value;

	public Agent getOwner() {
		return owner;
	}

	@Override
	public boolean isActive(long val) {
		return true;
	}

	@Override
	public void reset(long time, int weight, float rate) {

	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	private static final Color MY_COLOR = new Color(128, 0, 255);

	@Override
	public Color getColor() {
		return MY_COLOR;
	}

	@Override
	public boolean canStep() {
		return true;
	}

	@Override
	public void expire() {
		productionMapper.remProduct(this);
	}

	public Location getLocation() {
		return loc;
	}

	@Override
	public void onStep(ComplexAgent agent) {
		if (owner != agent && globals.random.nextFloat() <= 0.3f) {
			productionMapper.remProduct(this);
			// TODO: Reward p.owner for selling his product!
			// Reward this agent for buying a product! (and punish it
			// for paying for it?)
			// Should agents have currency to use for buying products?

		}
	}

}