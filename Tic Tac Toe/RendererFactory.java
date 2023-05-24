public class RendererFactory {

    public RendererFactory() {
    }

    public Renderer buildRenderer(String typeOfRendererInput) {
        switch (typeOfRendererInput) {
            case "console":
                return new ConsoleRenderer();

            case "none":
                return new VoidRenderer();

            default:
                return null;
        }
    }
}
