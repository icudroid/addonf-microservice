/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { AdminBoTestModule } from '../../../test.module';
import { MediaTargetComponent } from '../../../../../../main/webapp/app/entities/media-target/media-target.component';
import { MediaTargetService } from '../../../../../../main/webapp/app/entities/media-target/media-target.service';
import { MediaTarget } from '../../../../../../main/webapp/app/entities/media-target/media-target.model';

describe('Component Tests', () => {

    describe('MediaTarget Management Component', () => {
        let comp: MediaTargetComponent;
        let fixture: ComponentFixture<MediaTargetComponent>;
        let service: MediaTargetService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [MediaTargetComponent],
                providers: [
                    MediaTargetService
                ]
            })
            .overrideTemplate(MediaTargetComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaTargetComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaTargetService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MediaTarget(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.mediaTargets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
